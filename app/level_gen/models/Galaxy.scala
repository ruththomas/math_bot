package level_gen.models
import play.api.libs.json.{Json, OFormat}
object Galaxy {
  implicit val format: OFormat[Galaxy] = Json.format[Galaxy]
}
case class Galaxy(name: String, starSystems: List[StarSystem])
