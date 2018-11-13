package actors.convert_flow
import actors.AdminActor.UserCount
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OWrites}

object AdminResponseConvertFlow extends SocketResponseConvertFlow {
  final case class AdminResponse(
      status: String,
      userCount: Option[String] = None,
      message: Option[String] = None
  )
  implicit val adminResponseWrites: OWrites[AdminResponse] = Json.format[AdminResponse]

  final val success = "success"
  final val failed = "failed"

  override def responseToJson(msg: Any): JsValue = {
    Json.toJson[AdminResponse](msg match {
      case UserCount(count) => AdminResponse(success, userCount = Some(count))
      case ActorFailed(message) => AdminResponse(failed, message = Some(message))
      case _ => AdminResponse(failed, None, None)
    })
  }
  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
