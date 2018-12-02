package actors.messages.level

import play.api.libs.json.Json

object HintTaken {
  implicit val hintTakenFormat = Json.format[HintTaken]

  final val continentIdLabel: String = "continentId"
  final val timeStampLabel: String = "timeStamp"
  final val countLabel: String = "count"
  final val starsLabel: String = "stars"
}
/*
 * HintTaken
 * */
case class HintTaken(
    continentId: String,
    timeStamp: Long,
    count: Int,
    stars: Int
)
