package actors.messages

import play.api.libs.json.{Json, OFormat}

object AssignedFunction {
  implicit val assignedStagedModelFormat: OFormat[AssignedFunction] = Json.format[AssignedFunction]
}

case class AssignedFunction(name: String, createdId: String, image: String, sizeLimit: Int, func: List[String])
