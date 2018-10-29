package actors.messages.level

import java.util.Date
import play.api.libs.json.{Json, OFormat}

object LayerStatistic {
  implicit val statisticsFormat: OFormat[LayerStatistic] = Json.format[LayerStatistic]

  final val nameLabel: String = "name"
  final val activeLabel: String = "active"
  final val winsLabel: String = "wins"
  final val timesPlayedLabel: String = "timesPlayed"
  final val lastPlayedLabel: String = "lastPlayed"
}

case class LayerStatistic(
    name: String,
    active: Boolean = false,
    wins: Int = 0,
    timesPlayed: Int = 0,
    lastPlayed: Option[Date] = None
)
