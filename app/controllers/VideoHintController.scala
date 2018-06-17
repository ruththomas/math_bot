package controllers

import actors.convert_flow.{VideoRequestConvertFlow, VideoResponseConvertFlow}
import actors.VideoHintActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.Inject
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.mvc.{Controller, WebSocket}

class VideoHintController @Inject()(implicit val system: ActorSystem, implicit val mat: Materializer)
    extends Controller {

  private type WSMessage = JsValue

  def videoSocket: WebSocket = WebSocket.accept[WSMessage, WSMessage] { _ =>
    VideoRequestConvertFlow()
      .via(
        ActorFlow.actorRef { out =>
          VideoHintActor.props(out)
        }
      )
      .via(VideoResponseConvertFlow())
  }
}
