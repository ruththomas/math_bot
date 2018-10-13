package actors.convert_flow
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OFormat}

object LevelRequestConvertFlow extends SocketRequestConvertFlow {
  implicit val adminRequestFormat: OFormat[AdminRequest] = Json.format[AdminRequest]
  final case class AdminRequest(
      action: String
  )
  override def jsonToCommand(msg: JsValue): Any = {
    Json.fromJson[AdminRequest](msg).asOpt match {
      case _ => ActorFailed("Bad json input")
    }
  }
  override def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCommand)
  }
}
