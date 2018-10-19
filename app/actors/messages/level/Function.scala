package actors.messages.level

import play.api.libs.json.{Json, OFormat}

object Function {
  implicit val format: OFormat[Function] = Json.format[Function]

  final val active = "active"
  final val inactive = "inactive"
}

case class Function(
    created_id: String,
    color: String = "default",
    func: Option[List[Function]],
    set: Boolean = false,
    name: String,
    image: String,
    index: Int,
    category: String,
    commandId: String,
    sizeLimit: Int = -1
)
