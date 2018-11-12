package actors.convert_flow

import actors.messages.CompilerRequest
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json, Reads}

trait SocketRequestConvertFlow {
  implicit val sockRequestReads: Reads[CompilerRequest] = Json.reads[CompilerRequest]
  def jsonToCommand(msg: JsValue): Any
  def apply(): Flow[JsValue, Any, NotUsed]
}
