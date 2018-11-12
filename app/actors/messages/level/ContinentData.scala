package actors.messages.level
import play.api.libs.json.{Json, OFormat}
import types.ContinentId

object ContinentData {
  implicit val format: OFormat[ContinentData] = Json.format[ContinentData]
}

case class ContinentData(id: ContinentId, stats: LayerStatistic)
