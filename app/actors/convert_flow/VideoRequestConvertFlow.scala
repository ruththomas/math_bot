package actors.convert_flow

import actors.messages._
import akka.NotUsed
import akka.stream.scaladsl.Flow
import play.api.libs.json.{JsValue, Json}

object VideoRequestConvertFlow extends SocketRequestConvertFlow {

  def jsonToCompilerCommand(msg: JsValue): Any = {
    Json.fromJson[SocketRequest](msg).asOpt match {
      case _ => ActorFailed("Invalid socket request json.")
    }
  }

  def apply(): Flow[JsValue, Any, NotUsed] = {
    Flow[JsValue].map(jsonToCompilerCommand)
  }
}
