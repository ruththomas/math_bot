package actors.convert_flow

import actors.messages.SocketRequest
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, Reads}

trait SocketRequestConvertFlow {
  implicit val sockRequestReads: Reads[SocketRequest] = Json.reads[SocketRequest]
  def jsonToCompilerCommand(msg: JsValue): Any
  def apply(): Flow[JsValue, Any, NotUsed]
}
