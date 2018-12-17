package actors.messages.level
import compiler.Element
import play.api.libs.json.{Json, OFormat}

object ClientElement {
  implicit val clientElementFormat: OFormat[ClientElement] = Json.format[ClientElement]

  def apply(elementKind: Element): ClientElement =
    new ClientElement(elementKind.original, elementKind.name, elementKind.value)
}

case class ClientElement(original: Boolean, name: String, value: Double)
