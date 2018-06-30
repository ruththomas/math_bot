package actors.messages

import play.api.libs.functional.syntax._
import play.api.libs.json._
import types.{LevelName, StepName}

case class RawLevelData(level: LevelName,
                        prevLevel: LevelName,
                        nextLevel: LevelName,
                        show: Boolean,
                        steps: Map[String, RawStepData])

object RawLevelData {
  val levelDataReads: Reads[RawLevelData] = (
    (JsPath \ "level").read[LevelName] and
    (JsPath \ "prevLevel").read[LevelName] and
    (JsPath \ "nextLevel").read[LevelName] and
    (JsPath \ "show").read[Boolean] and
    (JsPath \ "steps").read[Map[StepName, RawStepData]]
  )(RawLevelData.apply _)

  val levelDataWrites: Writes[RawLevelData] = (
    (JsPath \ "level").write[LevelName] and
    (JsPath \ "prevLevel").write[LevelName] and
    (JsPath \ "nextLevel").write[LevelName] and
    (JsPath \ "show").write[Boolean] and
    (JsPath \ "steps").write[Map[StepName, RawStepData]]
  )(unlift(RawLevelData.unapply))

  implicit val levelDataFormat: Format[RawLevelData] =
    Format(levelDataReads, levelDataWrites)
}
