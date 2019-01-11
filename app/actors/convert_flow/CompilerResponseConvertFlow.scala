package actors.convert_flow

import actors.messages._
import akka.NotUsed
import akka.stream.scaladsl.Flow
import models.compiler
import models.compiler._
import play.api.libs.json.{JsValue, _}

object CompilerResponseConvertFlow extends SocketResponseConvertFlow {

  implicit val compileResponseFormat: OWrites[CompilerResponse] = Json.writes[CompilerResponse]
  implicit val compilerResponseLegacyFormat: OWrites[CompilerResponseLegacy] = Json.writes[CompilerResponseLegacy]

  private def responseToJson: PartialFunction[Any, CompilerResponse] = {
    case CompilerOutput(frames) => compiler.CompilerResponse(frames)
    case _: CompilerHalted => CompilerResponse(halted = Some(true))
    case ActorFailed(m) => CompilerResponse(error = Some(m))
    case _ => CompilerResponse(error = Some("Unknown response from compiler"))
  }

  private def responseToJsonLegacy: PartialFunction[Any, CompilerResponseLegacy] = {
    case CompilerOutputLegacy(frames) => compiler.CompilerResponseLegacy(frames)
  }

  def apply(): Flow[Any, JsValue, NotUsed] =
    Flow[Any].map(
      (responseToJsonLegacy andThen (Json.toJson(_)))
      orElse
      (responseToJson andThen (Json.toJson(_)))
    )
}
