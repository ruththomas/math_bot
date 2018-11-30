package actors.convert_flow

import actors.VideoHintActor.{GetHintData, GetHintsTaken, GetVideo}
import actors.messages._
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, Reads}

object VideoRequestConvertFlow extends SocketRequestConvertFlow {
  final case class VideoRequest(action: String, path: Option[String] = None)

  implicit val videoRequestFormat: Reads[VideoRequest] = Json.format[VideoRequest]

  def jsonToCommand(msg: JsValue): Any = {
    Json.fromJson[VideoRequest](msg).asOpt match {
      case Some(VideoRequest(action, _)) if action == "get-hint" =>
        GetVideo()
      case Some(VideoRequest(action, _)) if action == "get-hints-taken" =>
        GetHintsTaken()
      case Some(VideoRequest(action, Some(path))) if action == "get-continent-hints" =>
        GetHintData(path)
      case _ => ActorFailed("Invalid socket request json.")
    }
  }

  def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCommand)
  }
}
