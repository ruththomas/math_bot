package level_gen.models
import actors.messages.AssignedFunction
import play.api.libs.json.{Json, OFormat}

object ContinentStruct {
  implicit val format: OFormat[ContinentStruct] = Json.format[ContinentStruct]
}

case class ContinentStruct(
    gridMap: List[String],
    description: String,
    mainMax: Int,
    robotOrientation: Int,
    stagedEnabled: Boolean,
    activeEnabled: Boolean,
    stagedQty: Int,
    assignedStaged: List[AssignedFunction],
    preBuiltActive: List[AssignedFunction],
    allowedActives: Option[List[String]] = None,
    cmdsAvailable: List[String],
    specialParameters: List[String],
    problem: String,
    clearMain: Boolean,
    initFocus: List[String],
    evalEachFrame: Option[Boolean] = None,
    videoHints: List[String],
    freeHint: Option[String] = None
) {
  def maxMain: Int = this.mainMax match { case -1 => 10000; case _ => this.mainMax }
}
