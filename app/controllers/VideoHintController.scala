package controllers

import actors.convert_flow.{VideoRequestConvertFlow, VideoResponseConvertFlow}
import actors.VideoHintActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.Inject
import model.{PlayerTokenDAO, VideoHintDAO}
import play.api.Environment
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.mvc.{Controller, WebSocket}
import play.api.libs.ws._
class VideoHintController @Inject()(implicit val system: ActorSystem,
                                    implicit val mat: Materializer,
                                    playerTokenDAO: PlayerTokenDAO,
                                    videoHintDAO: VideoHintDAO,
                                    ws: WSClient,
                                    environment: Environment)
    extends Controller {

  private type WSMessage = JsValue

  def videoSocket: WebSocket = WebSocket.accept[WSMessage, WSMessage] { _ =>
    VideoRequestConvertFlow()
      .via(
        ActorFlow.actorRef { out =>
          VideoHintActor.props(out, playerTokenDAO, videoHintDAO, ws, environment)
        }
      )
      .via(VideoResponseConvertFlow())
  }
}
