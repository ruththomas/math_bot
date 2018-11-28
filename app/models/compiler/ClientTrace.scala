package models.compiler

import compiler.operations.UserFunctionById
import compiler.processor.TraceTag
import play.api.libs.json.{ Json, OWrites }

case class ClientTrace(created_id: String, index: Int)

object ClientTrace {

  implicit val clientTraceWrites : OWrites[ClientTrace] = Json.writes[ClientTrace]

  def apply(traceTag: TraceTag) : ClientTrace =
    traceTag.function match {
    case UserFunctionById(_, created_id) =>
      ClientTrace(created_id, traceTag.index )
    case _ =>
      ClientTrace("unknown", traceTag.index)
  }
}
