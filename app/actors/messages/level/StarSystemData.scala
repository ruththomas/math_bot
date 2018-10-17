package actors.messages.level
import play.api.libs.json.{Json, OFormat}
import types.{StarSystemId, TokenId}

object StarSystemData {
  implicit val format: OFormat[StarSystemData] = Json.format[StarSystemData]
}

case class StarSystemData(
    id: StarSystemId,
    stats: LayerStatistic,
    planets: Option[List[PlanetData]]
)
