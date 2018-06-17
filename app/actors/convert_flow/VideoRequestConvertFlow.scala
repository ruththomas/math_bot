package actors.convert_flow

import actors.VideoHintActor.GetVideo
import actors.messages._
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, Reads}
import types.{LevelName, StepName, TokenId}

object VideoRequestConvertFlow extends SocketRequestConvertFlow {
  final case class VideoRequest(tokenId: TokenId, level: LevelName, step: StepName)

  implicit val videoRequestFormat: Reads[VideoRequest] = Json.format[VideoRequest]

  def jsonToCompilerCommand(msg: JsValue): Any = {
    Json.fromJson[VideoRequest](msg).asOpt match {
      case Some(VideoRequest(tokenId, level, step)) =>
        GetVideo(tokenId, level, step)
      case _ => ActorFailed("Invalid socket request json.")
    }
  }

  def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCompilerCommand)
  }
}
