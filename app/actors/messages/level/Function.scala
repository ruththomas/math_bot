package actors.messages.level

import play.api.libs.json.{Json, OFormat}

object Function {
  implicit val format: OFormat[Function] = Json.format[Function]

  final val activeLabel = "active"
  final val inactiveLabel = "inactive"
  final val tokenIdLabel = "tokenId"
  final val funcLabel = "func"
  final val colorLabel = "color"
  final val createdIdLabel = "created_id"
  final val setLabel = "set"
  final val nameLabel = "name"
  final val imageLabel = "image"
  final val indexLabel = "index"
  final val categoryLabel = "category"
  final val commandIdLabel = "commandId"
  final val sizeLimitLabel = "sizeLimit"
}

case class Function(
    created_id: String,
    color: String = "default",
    func: Option[List[Function]],
    displayName: Option[Boolean] = None,
    set: Boolean = false,
    name: String,
    image: String,
    index: Int,
    category: String,
    commandId: String,
    sizeLimit: Int = -1
) {
  def clientSizeLimit: Int = if (this.sizeLimit < 0) 10000 else this.sizeLimit
}
