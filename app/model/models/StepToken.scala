package model.models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Json, Reads}

case class StepToken(
    name: String,
    timesPlayed: Int = 0,
    wins: Option[Int] = None,
    stars: Int = 0,
    active: Boolean = false,
    prevStep: String = "None",
    nextStep: String,
    prevLevel: String = "None",
    nextLevel: String = "None"
)

object StepToken {
  implicit val jsonFormat = Json.format[StepToken]
}
