package actors.convert_flow
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import model.models.Stats
import play.api.libs.json.{JsValue, Json, Writes}

object VideoResponseConvertFlow extends SocketResponseConvertFlow {
  final case class VideoResponse(status: String, stats: Option[Stats])

  implicit val videoResponseWrites: Writes[VideoResponse] = Json.format[VideoResponse]

  override def responseToJson(msg: Any): JsValue = {
    val cr = msg match {
      case ActorFailed(msg) => VideoResponse(status = msg, None)
      case _ => VideoResponse(status = "failed", stats = None)
    }
    Json.toJson[VideoResponse](cr)
  }

  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
