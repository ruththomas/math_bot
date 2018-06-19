package actors.convert_flow
import actors.VideoHintActor.{Hints, VideoCompiled}
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import model.models.{Stats, VideoHint}
import play.api.libs.json.{JsValue, Json, Writes}
import types.URL

object VideoResponseConvertFlow extends SocketResponseConvertFlow {
  final case class VideoResponse(status: String,
                                 message: Option[String],
                                 videoURL: Option[URL],
                                 stats: Option[Stats],
                                 hints: Option[VideoHint])

  implicit val videoResponseWrites: Writes[VideoResponse] = Json.format[VideoResponse]

  override def responseToJson(msg: Any): JsValue = {
    val cr = msg match {
      case VideoCompiled(_, videoUrl, stats) => VideoResponse("success", None, Some(videoUrl), Some(stats), None)
      case Hints(videoHint) => VideoResponse("success", None, None, None, Some(videoHint))
      case ActorFailed(message) => VideoResponse("failed", Some(message), None, None, None)
      case _ => VideoResponse("failed", None, None, None, None)
    }
    Json.toJson[VideoResponse](cr)
  }

  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
