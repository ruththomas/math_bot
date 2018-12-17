package actors.messages.level

import compiler.ElementKinds._
import play.api.libs.json.{Json, OFormat}

object GridPart {
  implicit val jsonFormat: OFormat[GridPart] = Json.format[GridPart]

  private def getElement(str: String)(cStr: String = str.replace("[", "").replace("]", "")): Option[ClientElement] = {
    listedElements
      .find(_.name == cStr)
      .map(ClientElement.apply)
  }

  def setTools(str: String): List[ClientElement] = {
    str
      .replaceFirst("TS", "")
      .replaceAll("\\[", "")
      .replaceAll("\\]", "")
      .replaceAll("\\(", "")
      .replaceAll("\\)", "")
      .split(",")
      .toList
      .flatMap(getElement(_)().map(_.copy(original = false)))
  }

  def apply(key: String): GridPart = key match {
    case "(R)" => new GridPart(name = "empty space", robotSpot = true)
    case "($)" => new GridPart("final answer")
    case "|E|" => new GridPart("empty space")
    case "|W|" => new GridPart("wall")
    case k if k contains "[TS" => new GridPart(name = "empty space", tools = setTools(k))
    case k if k contains "[" =>
      new GridPart(name = "empty space", tools = getElement(k)().toList)
    case _ => new GridPart("empty space")
  }
}

case class GridPart(name: String, robotSpot: Boolean = false, tools: List[ClientElement] = List.empty[ClientElement])
