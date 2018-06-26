package actors.convert_flow
import actors.VideoHintActor.{HintPrepared, NoVideos, RemainingTime, RemainingTimeList}
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import model.models.HintTaken
import play.api.libs.json.{JsValue, Json, Writes}
import types.URL

object VideoResponseConvertFlow extends SocketResponseConvertFlow {
  implicit val remainingTimeWrites = Json.format[RemainingTime]

  final case class VideoResponse(status: String,
                                 message: Option[String],
                                 videoURL: Option[URL],
                                 remainingTimes: Option[List[RemainingTime]],
                                 remainingTime: Option[RemainingTime])

  implicit val videoResponseWrites: Writes[VideoResponse] = Json.format[VideoResponse]

  override def responseToJson(msg: Any): JsValue = {
    val cr = msg match {
      case HintPrepared(_, videoUrl, remainingTime) =>
        VideoResponse("success", None, Some(videoUrl), None, Some(remainingTime))
      case RemainingTimeList(_, remainingTimes) =>
        VideoResponse("success", None, None, Some(remainingTimes), None)
      case NoVideos(_, level, step) =>
        VideoResponse("no-videos", None, None, None, None)
      case ActorFailed(message) => VideoResponse("failed", Some(message), None, None, None)
      case _ => VideoResponse("failed", None, None, None, None)
    }
    Json.toJson[VideoResponse](cr)
  }

  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
