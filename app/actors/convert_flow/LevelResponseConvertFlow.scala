package actors.convert_flow
import actors.messages.ActorFailed
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, OWrites}

object LevelResponseConvertFlow extends SocketResponseConvertFlow {
  implicit val levelResponseWrites: OWrites[LevelResponse] = Json.format[LevelResponse]
  final case class LevelResponse(
      status: String
  )

  final val success = "success"
  final val failed = "failed"

  override def responseToJson(msg: Any): JsValue = {
    Json.toJson[LevelResponse](msg match {
      case ActorFailed(message) => LevelResponse(failed)
      case _ => LevelResponse(failed)
    })
  }
  override def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
