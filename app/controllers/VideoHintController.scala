package controllers

import actors.{LevelControl, VideoHintActor}
import actors.convert_flow.{VideoRequestConvertFlow, VideoResponseConvertFlow}
import akka.actor.ActorSystem
import akka.http.scaladsl.util.FastFuture
import akka.stream.Materializer
import com.google.inject.Inject
import daos.{PlayerTokenDAO, SessionDAO, StatsDAO, VideoHintDAO}
import play.api.Environment
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.libs.ws._
import play.api.mvc.{Controller, WebSocket}
import utils.SecureIdentifier

import scala.concurrent.ExecutionContext
class VideoHintController @Inject()(implicit val system: ActorSystem,
                                    implicit val mat: Materializer,
                                    val statsDAO: StatsDAO, // may not need this
                                    val videoHintDAO: VideoHintDAO,
                                    val sessionDAO: SessionDAO,
                                    val levelControl: LevelControl,
                                    ws: WSClient,
                                    environment: Environment,
                                    implicit val ec: ExecutionContext)
    extends Controller {

  private type WSMessage = JsValue

  def videoSocket: WebSocket = WebSocket.acceptOrResult[WSMessage, WSMessage] { implicit request =>
    request.cookies.get("player-session").map(c => SecureIdentifier(c.value)) match {
      case Some(sessionId) =>
        sessionDAO.find(sessionId).map {
          case Some(session) =>
            Right(
              VideoRequestConvertFlow()
                .via(
                  ActorFlow.actorRef { out =>
                    VideoHintActor
                      .props(out, session.playerTokenId, statsDAO, levelControl, videoHintDAO, ws, environment)
                  }
                )
                .via(VideoResponseConvertFlow())
            )
          case None => Left(Unauthorized("Session invalid"))
        }
      case None => FastFuture.successful(Left(Unauthorized("No cookie")))
    }
  }
}
