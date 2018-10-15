package level_gen.models
import play.api.libs.json.{Json, OFormat}
object StarSystem {
  implicit val format: OFormat[StarSystem] = Json.format[StarSystem]
}
case class StarSystem(name: String, planets: List[Planet])
