package actors.messages.level
import play.api.libs.json.{Json, OFormat}

object PathAndContinent {
  implicit val format: OFormat[PathAndContinent] = Json.format[PathAndContinent]
}

case class PathAndContinent(path: String, builtContinent: BuiltContinent)
