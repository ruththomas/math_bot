package actors.messages

import play.api.libs.json.{Json, OFormat}

object AssignedFunctionModel {
  implicit val assignedStagedModelFormat: OFormat[AssignedFunctionModel] = Json.format[AssignedFunctionModel]
}

case class AssignedFunctionModel(name: String, image: String, sizeLimit: Int, func: List[String])
