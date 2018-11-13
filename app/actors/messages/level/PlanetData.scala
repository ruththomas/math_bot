package actors.messages.level
import play.api.libs.json.{Json, OFormat}
import types.PlanetId

object PlanetData {
  implicit val format: OFormat[PlanetData] = Json.format[PlanetData]
}

case class PlanetData(
    id: PlanetId,
    stats: LayerStatistic,
    continents: List[ContinentData]
)
