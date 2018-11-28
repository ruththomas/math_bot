package controllers

import actors._
import actors.convert_flow.{ CompilerRequestConvertFlow, CompilerResponseConvertFlow, UpdateAccessFlow }
import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.util.FastFuture
import akka.stream.Materializer
import com.google.inject.name.Named
import configuration.CompilerConfiguration
import daos.{ PlayerAccountDAO, PlayerTokenDAO, SessionDAO }
import javax.inject.Inject
import loggers.MathBotLogger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import play.api.{ Configuration, Environment }
import utils.SecureIdentifier

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
}
