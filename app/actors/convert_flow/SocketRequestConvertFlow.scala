package actors.convert_flow

import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.JsValue

trait SocketRequestConvertFlow {
  def jsonToCommand(msg: JsValue): Any
  def apply(): Flow[JsValue, Any, NotUsed]
}
