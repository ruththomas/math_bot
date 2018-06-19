package actors.convert_flow

import actors.VideoHintActor.{GetHintsTaken, GetVideo}
import actors.messages._
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, Reads}
import types.{LevelName, StepName, TokenId}

object VideoRequestConvertFlow extends SocketRequestConvertFlow {
  final case class VideoRequest(action: String, tokenId: Option[TokenId])

  implicit val videoRequestFormat: Reads[VideoRequest] = Json.format[VideoRequest]

  def jsonToCompilerCommand(msg: JsValue): Any = {
    Json.fromJson[VideoRequest](msg).asOpt match {
      case Some(VideoRequest(action, Some(tokenId))) if action == "get-hint" =>
        GetVideo(tokenId)
      case Some(VideoRequest(action, Some(tokenId))) if action == "get-hints-taken" =>
        GetHintsTaken(tokenId)
      case _ => ActorFailed("Invalid socket request json.")
    }
  }

  def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCompilerCommand)
  }
}
