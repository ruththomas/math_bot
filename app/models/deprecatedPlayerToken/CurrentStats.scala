package models.deprecatedPlayerToken

import models.StepToken
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Reads, _}

object CurrentStats {
  val statsReads: Reads[CurrentStats] = (
    (JsPath \ "level").read[String] and
    (JsPath \ "step").read[String] and
    (JsPath \ "levels").read[Map[String, Map[String, StepToken]]]
  )(CurrentStats.apply _)

  val statsWrites: Writes[CurrentStats] = (
    (JsPath \ "level").write[String] and
    (JsPath \ "step").write[String] and
    (JsPath \ "levels").write[Map[String, Map[String, StepToken]]]
  )(unlift(CurrentStats.unapply))

  implicit val funcTokenFormat: Format[CurrentStats] =
    Format(statsReads, statsWrites)
}

case class CurrentStats(
    level: String,
    step: String,
    levels: Map[String, Map[String, StepToken]]
)
