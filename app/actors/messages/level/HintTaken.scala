package actors.messages.level

import play.api.libs.json.Json
import types.ContinentId

object HintTaken {
  implicit val hintTakenFormat = Json.format[HintTaken]

  final val continentIdLabel: String = "continentId"
  final val timeStampLabel: String = "timeStamp"
  final val countLabel: String = "count"
}
/*
 * HintTaken
 * */
case class HintTaken(
    continentId: ContinentId,
    timeStamp: Long,
    count: Int,
    stars: Int
)
