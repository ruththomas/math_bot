package actors.messages.level

import java.util.Date

import play.api.libs.json.{Json, OFormat}

object LayerStatistic {
  implicit val statisticsFormat: OFormat[LayerStatistic] = Json.format[LayerStatistic]
}

case class LayerStatistic(
    name: String,
    active: Boolean = false,
    wins: Int = 0,
    timesPlayed: Int = 0,
    lastPlayed: Option[Date] = None
)
