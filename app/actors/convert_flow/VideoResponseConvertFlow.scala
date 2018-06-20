package actors.convert_flow
import actors.VideoHintActor.VideoCompiled
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import model.models.Stats
import play.api.libs.json.{JsValue, Json, Writes}
import types.URL

object VideoResponseConvertFlow extends SocketResponseConvertFlow {
  final case class VideoResponse(status: String, videoURL: Option[URL], stats: Option[Stats])

  implicit val videoResponseWrites: Writes[VideoResponse] = Json.format[VideoResponse]

  override def responseToJson(msg: Any): JsValue = {
    val cr = msg match {
      case VideoCompiled(tokenId, videoUrl, stats) => VideoResponse("success", Some(videoUrl), Some(stats))
      case ActorFailed(msg) => VideoResponse("failed", None, None)
      case _ => VideoResponse("failed", None, None)
    }
    Json.toJson[VideoResponse](cr)
  }

  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
