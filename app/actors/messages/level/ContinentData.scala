package actors.messages.level
import play.api.libs.json.{Json, OFormat}

object ContinentData {
  implicit val format: OFormat[ContinentData] = Json.format[ContinentData]
}

case class ContinentData(id: String, stats: LayerStatistic)
