package actors.convert_flow
import actors.VideoHintActor.{ HintPrepared, NoHints, RemainingTime, RemainingTimeList }
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{ JsValue, Json, OFormat, Writes }

object VideoResponseConvertFlow extends SocketResponseConvertFlow {
  implicit val remainingTimeWrites: OFormat[RemainingTime] = Json.format[RemainingTime]

  final case class VideoResponse(status: String,
                                 message: Option[String] = None,
                                 videoURL: Option[String] = None,
                                 remainingTimes: Option[List[RemainingTime]] = None,
                                 remainingTime: Option[RemainingTime] = None)

  implicit val videoResponseWrites: Writes[VideoResponse] = Json.format[VideoResponse]

  private def responseToJson(msg: Any): JsValue = {
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
