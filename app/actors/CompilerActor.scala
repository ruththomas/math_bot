package actors

import actors.LevelGenerationActor.GetGridMap
import actors.StatsActor.{StatsDoneUpdating, UpdateStats}
import actors.messages._
import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import compiler.operations.NoOperation
import compiler.processor.{Frame, Processor}
import compiler.{Compiler, GridAndProgram}
import controllers.MathBotCompiler
import javax.inject.Inject
import loggers.MathBotLogger
import model.PlayerTokenModel
import model.models.GridMap
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.duration._

class CompilerActor @Inject()(out: ActorRef, tokenId: String)(
    val reactiveMongoApi: ReactiveMongoApi,
    statsActor: ActorRef,
    levelActor: ActorRef,
    logger: MathBotLogger
) extends Actor
    with PlayerTokenModel {

  import MathBotCompiler._

  case class ProgramState(stream: Stream[Frame],
                          iterator: Iterator[Frame],
                          grid: GridMap,
                          program: GridAndProgram,
                          clientFrames: List[ClientFrame] = List.empty[ClientFrame],
                          previousSteps: Int = 0,
                          leftoverFrame : Option[Frame] = None
                         )

  implicit val timeout: Timeout = 5000.minutes

  var currentCompiler: Option[ProgramState] = None

  private val className = "CompilerActor"

  override def receive: Receive = {
    case CompilerExecute(steps, problem) =>
      currentCompiler match {
        case Some(_) => self ! CompilerContinue(steps, problem)
        case None => self ! CompilerCreate(steps, problem)

      }

    case CompilerCreate(steps, problem) =>
      logger.LogInfo(className, "Creating new compiler.")

      for {
        tokenList <- getToken(tokenId)
        grid <- (levelActor ? GetGridMap(tokenList)).mapTo[GridMap]
      } yield {
        for {
          token <- tokenList
          main = token.lambdas.head.main
          funcs = token.lambdas.head.activeFuncs
          commands = token.lambdas.head.cmds
          program <- Compiler.compile( /*json,*/ main, funcs, commands, grid, problem)
        } yield {
          val processor = Processor(program)
          val stream = processor.execute()
          currentCompiler = Some(
            ProgramState(stream = stream, iterator = stream.iterator, program = program, grid = grid)
          )
          self ! CompilerContinue(steps, problem)
        }
      }

    case CompilerContinue(steps, problem) =>
      logger.LogInfo(className, s"Stepping compiler for $steps")
      for {
        programState <- currentCompiler
      } yield {
        // Ask to compiler to generate the requested number of execution frames
        val executeSomeFrames = programState.iterator
          .filter(f => f.robotLocation.isDefined) // filter out non-robot frames (eg function calls and program)
          .slice(programState.previousSteps, programState.previousSteps + steps)
          .toList

        // The list could be empty, which means the program has finished running and we should evaluate the last frame.
        def sendToRobot(leadingFrames : List[Frame]) ={
          val clientFrames = leadingFrames
            .map(f => ClientFrame(f))

          // Compress the grid to only cells that were changed by a frame.
          val taggedDuplicates = clientFrames
            .map(c => (c.robotState.grid, Set.empty[ClientCell]))
            .scanLeft((Option(ClientGrid(cells = Set.empty[ClientCell])), Set.empty[ClientCell])) {
              (previous, current) =>
                val changes = for {
                  p <- previous._1
                  c <- current._1
                } yield {
                  val prevDiff = p.cells diff c.cells
                  val currDiff = c.cells diff p.cells
                  val modifiedPart1 = (prevDiff ++ currDiff)
                    .groupBy(c => c.location)
                    .filter(g => g._2.size > 1)
                  val modifidePart2 =
                    c.cells.filter(p => modifiedPart1.contains(p.location))
                  val removed = (prevDiff diff currDiff).filter(p => !modifiedPart1.contains(p.location))
                  val newcells = (currDiff diff prevDiff).filter(p => !modifiedPart1.contains(p.location))
                  removed.map(c => c.copy(items = List.empty[String])) ++ modifidePart2 ++ newcells
                }
                (current._1, changes.getOrElse(Set.empty[ClientCell]))
            }
          val deduped = taggedDuplicates.tail
            .zip(clientFrames)
            .map(
              v =>
                v._2.copy(
                  robotState = v._2.robotState
                    .copy(grid = Some(ClientGrid(cells = v._1._2)))
              )
            )

          out ! CompilerOutput(deduped, problem)
        }

        def prepareLastFrame(frame : Frame)= {
          // Check to see if the last frame generated by the processor matches the problem check
          if (programState.grid
            .success(frame, programState.program.problem))
            {
              out ! CompilerOutput(List(MathBotCompiler.ClientFrame.success(frame)), problem)
              statsActor ! UpdateStats(success = true, tokenId)
            } else {
            out ! CompilerOutput(List(MathBotCompiler.ClientFrame.failure(frame)), problem)
          }
          currentCompiler = None
        }

        (currentCompiler.flatMap(_.leftoverFrame), executeSomeFrames) match {
          case (None, List(frame)) =>
            prepareLastFrame(frame)
          case (None, leadingFrames :+ last) =>
            sendToRobot(leadingFrames)
            currentCompiler = currentCompiler.map(_.copy(leftoverFrame = Some(last)))
          case (Some(leftover), leadingFrames :+ last) =>
            sendToRobot(leftover +: leadingFrames)
            currentCompiler = currentCompiler.map(_.copy(leftoverFrame = Some(last)))
          case (Some(leftover), Nil) =>
            prepareLastFrame(leftover)
          case (Some(leftover), List(last)) =>
            sendToRobot(List(leftover))
            currentCompiler = currentCompiler.map(_.copy(leftoverFrame = Some(last)))
          case (None, Nil) =>
          // This case does nothing, should never happen
        }
      }

    case _: CompilerHalt =>
      logger.LogInfo(className, "Compiler halted")
      currentCompiler = None
      out ! CompilerHalted()

<<<<<<< HEAD
    case Left(_: StatsDoneUpdating) =>
=======
    case Left(_ : StatsDoneUpdating) =>
>>>>>>> 356b7b41feb11a5d35b14b852cdd4ad6932800ef
      logger.LogInfo(className, s"Stats updated successfully. token_id:$tokenId")

    case Right(invalidJson: ActorFailed) =>
      logger.LogFailure(className, invalidJson.msg)
      self ! invalidJson.msg

    case _ => out ! ActorFailed("Unknown command submitted to compiler")
  }
}

object CompilerActor {
  def props(out: ActorRef,
            tokenId: String,
            reactiveMongoApi: ReactiveMongoApi,
            statsActor: ActorRef,
            levelActor: ActorRef,
            logger: MathBotLogger) =
    Props(new CompilerActor(out, tokenId)(reactiveMongoApi, statsActor, levelActor, logger))
}
