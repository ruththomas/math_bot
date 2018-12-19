package actors.convert_flow

import actors.messages._
import actors.messages.level.Problem
import akka.NotUsed
import akka.stream.scaladsl.Flow
import models.compiler
import models.compiler._
import play.api.libs.json.{ JsString, JsValue, Writes, _ }

object CompilerResponseConvertFlow extends SocketResponseConvertFlow {

  implicit val compileResponseFormat: OWrites[CompilerResponse] = Json.writes[CompilerResponse]

  def responseToJson(msg: Any): JsValue = {
    val cr = msg match {
      case CompilerOutput(frames) => compiler.CompilerResponse(frames)
      case _: CompilerHalted => CompilerResponse(halted = Some(true))
      case ActorFailed(m) => CompilerResponse(error = Some(m))
      case _ => CompilerResponse(error = Some("Unknown response from compiler"))
    }
    Json.toJson[CompilerResponse](cr)
  }

  def apply(): Flow[Any, JsValue, NotUsed] = Flow[Any].map(responseToJson)
}
