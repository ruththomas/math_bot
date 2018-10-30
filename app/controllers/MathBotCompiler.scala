package controllers

import actors._
import actors.convert_flow.{CompilerRequestConvertFlow, CompilerResponseConvertFlow, UpdateAccessFlow}
import actors.messages.ClientRobotState
import actors.messages.level.{LevelControl, PathAndContinent}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.util.FastFuture
import akka.stream.Materializer
import com.google.inject.name.Named
import compiler.processor.Frame
import compiler.{Cell, Point}
import configuration.CompilerConfiguration
import daos.{PlayerAccountDAO, PlayerTokenDAO, SessionDAO}
import javax.inject.Inject
import loggers.MathBotLogger
import models._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import play.api.{Configuration, Environment}
import utils.SecureIdentifier

object MathBotCompiler {

  case class ClientCell(location: Point, items: List[String])

  object ClientCell {
    def apply(loc: Point, cell: Cell): ClientCell = {
      val contents = cell.contents.map(element => element.image)
      ClientCell(loc, contents)
    }
  }

  case class ClientGrid(cells: Set[ClientCell])

  object ClientGrid {
    def apply(grid: compiler.Grid): ClientGrid = {
      val cells =
        grid.grid.map(g => ClientCell(Point(g._1._1, g._1._2), g._2)).toSet
      ClientGrid(cells)
    }
  }

  case class ClientFrame(robotState: ClientRobotState,
                         programState: String,
                         pathAndContinent: Option[PathAndContinent]) {
    def isSuccess() = programState == "success"
    def isFailure() = programState == "failure"
  }

  object ClientFrame {
    def apply(frame: Frame, pathAndContinent: Option[PathAndContinent] = None): ClientFrame =
      ClientFrame(frame, "running", pathAndContinent)

    // stepData is the step data to render at this point
    def success(frame: Frame, pathAndContinent: PathAndContinent): ClientFrame =
      ClientFrame(frame, "success", Some(pathAndContinent))

    // stepData is the step data to render at this point
    def failure(frame: Frame, pathAndContinent: PathAndContinent): ClientFrame =
      ClientFrame(frame, "failure", Some(pathAndContinent))

    def apply(frame: Frame, programState: String, pathAndContinent: Option[PathAndContinent]): ClientFrame =
      ClientFrame(ClientRobotState(frame), programState, pathAndContinent)
  }

  case class CompilerResponse(frames: List[ClientFrame] = List.empty[ClientFrame],
                              problem: Option[Problem] = None,
                              halted: Option[Boolean] = None,
                              error: Option[String] = None)

}

class MathBotCompiler @Inject()()(implicit system: ActorSystem,
                                  mat: Materializer,
                                  mathBotLogger: MathBotLogger,
                                  environment: Environment,
                                  configuration: Configuration,
                                  playerTokenDAO: PlayerTokenDAO,
                                  playerAccountDAO: PlayerAccountDAO,
                                  sessionDAO: SessionDAO,
                                  @Named(ActorTags.playerAccount) playerAccountActor: ActorRef,
                                  levelControl: LevelControl)
    extends Controller
    with utils.SameOriginCheck {

  val compilerConfiguration = CompilerConfiguration(
    maxProgramSteps = configuration.getInt("mathbot.maxProgramSteps").getOrElse(10000),
    maxEmptyLoopCount = configuration.getInt("mathbot.maxEmptyLoopCount").getOrElse(100)
  )

  def wsPath(connection: String): Action[AnyContent] = Action { implicit request =>
    val url = connection match {
      case p if p == "compiler" => routes.MathBotCompiler.compileWs().webSocketURL()
      case p if p == "videohint" => routes.VideoHintController.videoSocket().webSocketURL()
      case p if p == "level" => routes.LevelController.levelSocket().webSocketURL()
    }
    val changeSsl =
      if (url.contains("localhost")) url else url.replaceFirst("ws", "wss")
    Ok(changeSsl)
  }

  def compileWs(): WebSocket = WebSocket.acceptOrResult[JsValue, JsValue] { request =>
    request.cookies.get("player-session").map(c => SecureIdentifier(c.value)) match {
      case Some(sessionId) =>
        sessionDAO.find(sessionId).map {
          case Some(session) =>
            Right(
              CompilerRequestConvertFlow()
                .via(
                  ActorFlow.actorRef(
                    out =>
                      CompilerActor
                        .props(out, session.playerTokenId, mathBotLogger, compilerConfiguration, levelControl)
                  )
                )
                .alsoTo(UpdateAccessFlow(session.playerTokenId, playerAccountActor))
                .via(
                  CompilerResponseConvertFlow()
                )
            )
          case None => Left(Unauthorized("Session invalid"))
        }
      case None => FastFuture.successful(Left(Unauthorized("No cookie")))
    }
  }

//  def compile(encodedTokenId: String) = Action.async { implicit request =>
//    class FakeActor extends Actor {
//
//      override def receive: Receive = {
//        case _ =>
//      }
//    }
//
//    val fakeActorProps = Props(new FakeActor)
//    val fakeActor = system.actorOf(fakeActorProps)
//
//    implicit val timeout: Timeout = 500.seconds
//
//    request.body.asJson match {
//      case Some(json) =>
//        val sr = CompilerRequestConvertFlow.jsonToCommand(json)
//
//        val compilerProps =
//          CompilerActor.props(fakeActor,
//                              URLDecoder.decode(encodedTokenId, "UTF-8"),
//                              playerTokenDAO,
//                              levelActor,
//                              mathBotLogger,
//                              compilerConfiguration)
//        val compiler = system.actorOf(compilerProps)
//
//        (compiler ? sr)
//          .map(CompilerResponseConvertFlow.responseToJson)
//          .map(Ok(_))
//      case _ =>
//        Future(NoContent)
//    }

//  }
}
