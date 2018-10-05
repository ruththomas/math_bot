package actors.convert_flow
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OWrites}

object AdminResponseConvertFlow extends SocketResponseConvertFlow {
  final case class AdminResponse(
      status: String,
      jwt: Option[String]
  )
  implicit val adminResponseWrites: OWrites[AdminResponse] = Json.format[AdminResponse]

  final val success = "success"
  final val failed = "failed"

  override def responseToJson(msg: Any): JsValue = {
    Json.toJson[AdminResponse](msg match {
      case ActorFailed(message) => AdminResponse("failed", Some(message))
      case _ => AdminResponse(failed, None)
    })
  }
  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
