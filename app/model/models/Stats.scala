package model.models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Reads, _}
import types.{LevelName, StepName}

object Stats {
  val statsReads: Reads[Stats] = (
    (JsPath \ "level").read[LevelName] and
    (JsPath \ "step").read[StepName] and
    (JsPath \ "levels").read[Map[String, Map[String, StepToken]]]
  )(Stats.apply _)

  val statsWrites: Writes[Stats] = (
    (JsPath \ "level").write[LevelName] and
    (JsPath \ "step").write[StepName] and
    (JsPath \ "levels").write[Map[String, Map[String, StepToken]]]
  )(unlift(Stats.unapply))

  implicit val funcTokenFormat: Format[Stats] =
    Format(statsReads, statsWrites)
}

case class Stats(
    level: LevelName,
    step: StepName,
    levels: Map[String, Map[String, StepToken]]
)
