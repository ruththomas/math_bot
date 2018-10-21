package models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Reads, _}
import types.{LevelName, StepName}

object CurrentStats {
  val statsReads: Reads[CurrentStats] = (
    (JsPath \ "level").read[LevelName] and
    (JsPath \ "step").read[StepName] and
    (JsPath \ "levels").read[Map[String, Map[String, StepToken]]]
  )(CurrentStats.apply _)

  val statsWrites: Writes[CurrentStats] = (
    (JsPath \ "level").write[LevelName] and
    (JsPath \ "step").write[StepName] and
    (JsPath \ "levels").write[Map[String, Map[String, StepToken]]]
  )(unlift(CurrentStats.unapply))

  implicit val funcTokenFormat: Format[CurrentStats] =
    Format(statsReads, statsWrites)
}

case class CurrentStats(
    level: LevelName,
    step: StepName,
    levels: Map[String, Map[String, StepToken]]
)
