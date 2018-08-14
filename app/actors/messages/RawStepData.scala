package actors.messages

import play.api.libs.functional.syntax._
import play.api.libs.json._
import types.{LevelName, StepName}

case class RawStepData(
    level: LevelName,
    step: StepName,
    gridMap: List[String],
    description: String,
    mainMax: Int,
    robotOrientation: Int,
    stagedEnabled: Boolean,
    activeEnabled: Boolean,
    stagedQty: Int,
    assignedStaged: List[AssignedFunction],
    preBuiltActive: List[AssignedFunction],
    allowedActives: Option[List[String]],
    cmdsAvailable: List[String],
    specialParameters: List[String],
    problem: String,
    clearMain: Boolean,
    initFocus: List[String],
    evalEachFrame: Option[Boolean] = None,
    videoHints: List[String],
    prevStep: String,
    nextStep: String
)

object RawStepData {
  val stepDataReads: Reads[RawStepData] = (
    (JsPath \ "level").read[String] and
    (JsPath \ "step").read[String] and
    (JsPath \ "gridMap").read[List[String]] and
    (JsPath \ "description").read[String] and
    (JsPath \ "mainMax").read[Int] and
    (JsPath \ "robotOrientation").read[Int] and
    (JsPath \ "stagedEnabled").read[Boolean] and
    (JsPath \ "activeEnabled").read[Boolean] and
    (JsPath \ "stagedQty").read[Int] and
    (JsPath \ "assignedStaged").read[List[AssignedFunction]] and
    (JsPath \ "preBuiltActive").read[List[AssignedFunction]] and
    (JsPath \ "allowedActives").readNullable[List[String]] and
    (JsPath \ "cmdsAvailable").read[List[String]] and
    (JsPath \ "specialParameters").read[List[String]] and
    (JsPath \ "problem").read[String] and
    (JsPath \ "clearMain").read[Boolean] and
    (JsPath \ "initFocus").read[List[String]] and
    (JsPath \ "evalEachFrame").readNullable[Boolean] and
    (JsPath \ "videoHints").read[List[String]] and
    (JsPath \ "prevStep").read[String] and
    (JsPath \ "nextStep").read[String]
  )(RawStepData.apply _)

  val stepDataWrites: Writes[RawStepData] = (
    (JsPath \ "level").write[String] and
    (JsPath \ "step").write[String] and
    (JsPath \ "gridMap").write[List[String]] and
    (JsPath \ "description").write[String] and
    (JsPath \ "mainMax").write[Int] and
    (JsPath \ "robotOrientation").write[Int] and
    (JsPath \ "stagedEnabled").write[Boolean] and
    (JsPath \ "activeEnabled").write[Boolean] and
    (JsPath \ "stagedQty").write[Int] and
    (JsPath \ "assignedStaged").write[List[AssignedFunction]] and
    (JsPath \ "preBuiltActive").write[List[AssignedFunction]] and
    (JsPath \ "allowedActives").writeNullable[List[String]] and
    (JsPath \ "cmdsAvailable").write[List[String]] and
    (JsPath \ "specialParameters").write[List[String]] and
    (JsPath \ "problem").write[String] and
    (JsPath \ "clearMain").write[Boolean] and
    (JsPath \ "initFocus").write[List[String]] and
    (JsPath \ "evalEachFrame").writeNullable[Boolean] and
    (JsPath \ "videoHints").write[List[String]] and
    (JsPath \ "prevStep").write[String] and
    (JsPath \ "nextStep").write[String]
  )(unlift(RawStepData.unapply))

  implicit val stepDataFormat: Format[RawStepData] =
    Format(stepDataReads, stepDataWrites)
}
