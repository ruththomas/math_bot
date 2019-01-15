package actors.convert_flow

import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.JsValue

trait SocketResponseConvertFlow {
  def apply(): Flow[Any, JsValue, NotUsed]
}
