package actors.convert_flow
import actors.VideoHintActor.{HintPrepared, RemainingTime, RemainingTimeList}
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import model.models.{HintTaken, HintsTaken, Stats}
import play.api.libs.json.{JsValue, Json, Writes}
import types.URL

object VideoResponseConvertFlow extends SocketResponseConvertFlow {
  final case class VideoResponse(status: String,
                                 message: Option[String],
                                 videoURL: Option[URL],
                                 stats: Option[Stats],
                                 remainingTimes: Option[List[RemainingTime]],
                                 remainingTime: Option[Long])

  implicit val videoResponseWrites: Writes[VideoResponse] = Json.format[VideoResponse]

  override def responseToJson(msg: Any): JsValue = {
    val cr = msg match {
      case HintPrepared(_, videoUrl, stats) =>
        VideoResponse("success", None, Some(videoUrl), Some(stats), None, None)
      case RemainingTimeList(_, remainingTimes) =>
        VideoResponse("success", None, None, None, Some(remainingTimes), None)
      case ActorFailed(message) => VideoResponse("failed", Some(message), None, None, None, None)
      case RemainingTime(_, level, step, time) =>
        VideoResponse("success", Some(s"$level/$step"), None, None, None, Some(time))
      case _ => VideoResponse("failed", None, None, None, None, None)
    }
    Json.toJson[VideoResponse](cr)
  }

  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
