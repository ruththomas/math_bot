package actors.convert_flow
import actors.VideoHintActor.{HintPrepared, NoHints, RemainingTime, RemainingTimeList}
import actors.messages.ActorFailed
import actors.messages.level.{HintTaken, HintsTaken}
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, Writes}
import types.URL

object VideoResponseConvertFlow extends SocketResponseConvertFlow {
  implicit val remainingTimeWrites = Json.format[RemainingTime]

  final case class VideoResponse(status: String,
                                 message: Option[String] = None,
                                 videoURL: Option[URL] = None,
                                 remainingTimes: Option[List[RemainingTime]] = None,
                                 remainingTime: Option[RemainingTime] = None)

  implicit val videoResponseWrites: Writes[VideoResponse] = Json.format[VideoResponse]

  override def responseToJson(msg: Any): JsValue = {
    val cr = msg match {
      case HintPrepared(url, remainingTime) =>
        VideoResponse("success", videoURL = Some(url), remainingTime = Some(remainingTime))
      case RemainingTimeList(_, remainingTimes) =>
        VideoResponse("success", remainingTimes = Some(remainingTimes))
      case remainingTime: RemainingTime => VideoResponse("success", remainingTime = Some(remainingTime))
      case NoHints() => VideoResponse("no-hints", message = Some("no hints for continent"))
      case ActorFailed(message) => VideoResponse("failed", message = Some(message))
      case _ => VideoResponse("failed")
    }
    Json.toJson[VideoResponse](cr)
  }

  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
