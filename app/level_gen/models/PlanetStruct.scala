package level_gen.models
import play.api.libs.json.{Json, OFormat}

object PlanetStruct {
  implicit val format: OFormat[PlanetStruct] = Json.format[PlanetStruct]
}

case class PlanetStruct(
    description: String
)
