package models

import play.api.libs.json.Json

case class StepToken(
    name: String,
    timesPlayed: Int = 0,
    wins: Option[Int] = None,
    stars: Int = 0, // @deprecated
    active: Boolean = false,
    prevStep: String = "None",
    nextStep: String,
    prevLevel: String = "None",
    nextLevel: String = "None"
)

object StepToken {
  implicit val jsonFormat = Json.format[StepToken]
}
