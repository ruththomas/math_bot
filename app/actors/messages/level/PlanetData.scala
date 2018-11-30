package actors.messages.level
import play.api.libs.json.{Json, OFormat}

object PlanetData {
  implicit val format: OFormat[PlanetData] = Json.format[PlanetData]
}

case class PlanetData(
    id: String,
    stats: LayerStatistic,
    continents: List[ContinentData]
)
