package actors

import actors.messages._
import actors.messages.level.Problem
import akka.actor.{Actor, ActorRef, Props}
import akka.util.Timeout
import compiler.processor.{Frame, Processor}
import compiler.{Compiler, GridAndProgram, Point}
import configuration.CompilerConfiguration
import javax.inject.Inject
import loggers.MathBotLogger
import _root_.models.compiler.{ClientFrame, ClientRobotState, ClientTrace}
import akka.http.scaladsl.util.FastFuture
import compiler.operations.Final
import models.GridMap

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.concurrent.duration._

class CompilerActor @Inject()(out: ActorRef, tokenId: String)(
    logger: MathBotLogger,
    config: CompilerConfiguration,
    levelControl: LevelControl
) extends Actor {
  private implicit val ec: ExecutionContextExecutor = context.dispatcher

  implicit val timeout: Timeout = 5000.minutes

  private case class ProgramState(stream: Stream[Frame],
                                  iterator: Iterator[Frame],
                                  grid: GridMap,
                                  program: GridAndProgram,
                                  clientFrames: List[ClientFrame] = List.empty[ClientFrame],
                                  leftover: Option[Frame] = None,
                                  exitOnSuccess: Boolean = false) {
    def withLeftover(l: Frame): ProgramState = this.copy(leftover = Some(l))
  }

  private val className = s"CompilerActor(${context.self.path.toSerializationFormat})"

  private def createFrames(leadingFrames: List[Frame]): Future[List[ClientFrame]] = {

    val minimized = leadingFrames.map(_.withMinimalGrid)

    minimized.last match {
      case f: Frame if f.operation == Final =>
        for {
          // Update stats, and get the updated stats and the next continent
          pathAndContinent <- levelControl.advanceStats(tokenId, f.success)
        } yield {
          if (f.success) {
            minimized.init.map(ClientFrame(_)) :+ ClientFrame.success(f, pathAndContinent)
          } else {
            minimized.init.map(ClientFrame(_)) :+ ClientFrame.failure(f, pathAndContinent)
          }
        }

      case _ =>
        FastFuture.successful[List[ClientFrame]](minimized.map(ClientFrame(_)))

    }

  }

  private def createLastFromFromNothing() = {
    for {
      pathAndContinent <- levelControl.advanceStats(tokenId, success = true)
    } yield {
      ClientFrame(ClientRobotState(Point(0, 0), "0", List.empty[String]),
                  "failure",
                  Some(pathAndContinent),
                  Seq.empty[ClientTrace])
    }
  }

  /* Uncomment to help with debugging
  private def clientFramePrint(frame: ClientFrame): TokenId = {
    s"location: ${frame.robotState.location}\n" +
    s"register: ${frame.robotState.holding.mkString(", ")}\n" +
    "grid: \n" +
    frame.robotState.grid
      .map(cel => cel.cells.map(c => s"  (${c.location.x}, ${c.location.y} -> ${c.items.mkString(", ")}"))
      .map(_.mkString("\n"))
      .getOrElse(" No Grid")
  }
   */

  private def sendFrames(programState: ProgramState, clientFrames: List[ClientFrame]): Unit = {
    out ! CompilerOutput(clientFrames, programState.grid.problem)
  }

  override def receive: Receive = createCompile()

  def createCompile(): Receive = {
    case CompilerExecute(steps, problem) =>
      self ! CompilerCreate(steps, problem)

    case CompilerCreate(steps, problem) =>
      logger.LogInfo(className, "Creating new compiler.")

      for {
        continent <- levelControl.compilerBuiltContinent(tokenId)
      } yield {
        val main = continent.lambdas.main
        val funcs = continent.lambdas.activeFuncs
        val commands = continent.lambdas.cmds
        val grid = GridMap( // todo - refactor to use continent directly
          gMap = continent.gridMap,
          robotOrientation = continent.initialRobotState.orientation,
          success = continent.stepControl.success,
          problem = problem,
          evalEachFrame = continent.evalEachFrame,
          description = continent.description,
          toolList = continent.toolList
        )
        for {
          program <- Compiler.compile(main, funcs, commands, grid, problem)
        } yield {
          val processor = Processor(program, config)
          val stream = processor.execute(f => continent.stepControl.success(f, problem), continent.evalEachFrame)
          context.become(
            compileContinue(
              ProgramState(stream = stream,
                           iterator = stream.iterator,
                           grid = grid,
                           program = program,
                           exitOnSuccess = grid.evalEachFrame)
            )
          )
          self ! CompilerContinue(steps)
        }
      }
    case CompilerCreateMbl(steps, problem, mbl) =>
      logger.LogInfo(className, "Creating new compiler.")

      for {
        continent <- levelControl.compilerBuiltContinent(tokenId)
      } yield {
        val grid = GridMap( // todo - refactor to use continent directly
          gMap = continent.gridMap,
          robotOrientation = continent.initialRobotState.orientation,
          success = continent.stepControl.success,
          problem = problem,
          evalEachFrame = continent.evalEachFrame,
          description = continent.description,
          toolList = continent.toolList
        )

        Compiler.compileMbl(mbl, grid, problem) match {

          case Left(program) =>
            val processor = Processor(program, config)
            val stream = processor.execute(f => continent.stepControl.success(f, problem), continent.evalEachFrame)
            context.become(
              compileContinue(
                ProgramState(stream = stream,
                             iterator = stream.iterator,
                             grid = grid,
                             program = program,
                             exitOnSuccess = grid.evalEachFrame)
              )
            )
            self ! CompilerContinue(steps)
          case Right(error) =>
            out ! ActorFailed(error)
        }
      }

    case CompilerContinue(_) =>
      // We see this in production and don't know why the client is still sending continues after the actor
      // has signaled end of program.  So we try to create a fake last frame.
      logger.LogDebug("CompilerActor", "Continue received during create state.")
      for {
        lastFrame <- createLastFromFromNothing()
      } yield out ! CompilerOutput(List(lastFrame), Problem(""))
  }

  private def compileContinue(currentCompiler: ProgramState): Receive = {

    case CompilerExecute(steps, _) =>
      self ! CompilerContinue(steps)

    case CompilerContinue(steps) =>
      logger.LogInfo(className,
                     s"Stepping compiler for $steps steps with actor ${context.self.path.toSerializationFormat}")

      for {
        cf <- createFrames(
          currentCompiler.iterator
            .take(steps)
            .toList
        )
      } yield {
        sendFrames(currentCompiler, cf)
      }

    case _: CompilerHalt =>
      logger.LogInfo(className, "Compiler halted")
      context.become(createCompile())
      out ! CompilerHalted()

    case Right(invalidJson: ActorFailed) =>
      logger.LogFailure(className, invalidJson.msg)
      self ! invalidJson.msg

    case _ => out ! ActorFailed("Unknown command submitted to compiler")
  }

  override def postStop(): Unit = {
    logger.LogInfo(className, "Actor Stopped")
  }
}

object CompilerActor {
  def props(out: ActorRef,
            tokenId: String,
            logger: MathBotLogger,
            config: CompilerConfiguration,
            levelControl: LevelControl) =
    Props(new CompilerActor(out, tokenId)(logger, config, levelControl))
}
