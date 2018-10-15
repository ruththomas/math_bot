package level_gen.models
import play.api.libs.json.{Json, OFormat}
object Planet {
  implicit val format: OFormat[Planet] = Json.format[Planet]
}
case class Planet(name: String, continents: List[Continent])
