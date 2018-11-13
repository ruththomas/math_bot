package level_gen.models
import actors.messages.level.Stats
import play.api.libs.json.{Json, OFormat}

object CelestialSystem {
  implicit val format: OFormat[CelestialSystem] = Json.format[CelestialSystem]
}

case class CelestialSystem(
    name: String,
    kind: String,
    stats: Option[Stats] = None,
    starSystemStruct: Option[StarSystemStruct] = None,
    planetStruct: Option[PlanetStruct] = None,
    continentStruct: Option[ContinentStruct] = None,
    children: List[CelestialSystem] = List.empty[CelestialSystem]
)
