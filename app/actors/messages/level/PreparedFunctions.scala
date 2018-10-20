package actors.messages.level
import actors.LevelGenerationActor.makeQtyUnlimited
import actors.messages.AssignedFunction
import level_gen.models.ContinentStruct
import models.FuncToken
import play.api.libs.json.{Json, OFormat}

object PreparedFunctions {
  implicit val format: OFormat[PreparedFunctions] = Json.format[PreparedFunctions]

  private def getFunctionIds(listedFunctions: List[Function]): List[String] = listedFunctions.map(_.created_id)

  def makeAssignedStaged(assignedStaged: List[AssignedFunction], functionIds: List[String]): List[Function] = {
    assignedStaged
      .filterNot { as =>
        functionIds.contains(as.createdId)
      }
      .map { as =>
        Function(
          created_id = as.createdId,
          func = Some(List.empty[Function]),
          name = as.name,
          image = as.image,
          index = 0,
          commandId = "function",
          category = Categories.function,
          sizeLimit = makeQtyUnlimited(as.sizeLimit)
        )
      }
  }

  def makePrebuiltActives(prebuiltActives: List[AssignedFunction], functionIds: List[String]): List[Function] = {
    prebuiltActives
      .filterNot { pa =>
        functionIds.contains(pa.createdId)
      }
      .map { pa =>
        Function(
          created_id = pa.createdId,
          func = Some(List.empty[Function]),
          name = pa.name,
          image = pa.image,
          index = 0,
          commandId = "function",
          category = Categories.function,
          sizeLimit = makeQtyUnlimited(pa.sizeLimit)
        )
      }
  }

  def apply(functions: Functions, continentStruct: ContinentStruct): PreparedFunctions = {
    val listedFunctions = functions.list.values.toList
    val functionIds = getFunctionIds(listedFunctions)
    val allowedActivesIds = continentStruct.allowedActives.getOrElse(List.empty[String])
    val preBuildActivesIds = continentStruct.preBuiltActive.map(_.createdId)
    val assignedStagedIds = continentStruct.assignedStaged.map(_.createdId)

    new PreparedFunctions(
      main = listedFunctions
        .filter(f => f.category == Categories.main)
        .map { m =>
          m.copy(
            func = m.func.map {
              _.filter(f => if (allowedActivesIds.nonEmpty) allowedActivesIds.contains(f.created_id) else false)
              /*
             *   These may not be needed, will need to verify after more testing
             *  .filter(f => if (preBuildActivesIds.nonEmpty) preBuildActivesIds.contains(f.created_id) else false)
             *  .filter(f => if (assignedStagedIds.nonEmpty) assignedStagedIds.contains(f.created_id) else false)
             * */
            }
          )
        }
        .take(continentStruct.mainMax)
        .head,
      cmds = listedFunctions
        .filter(c => c.category == Categories.command)
        .filter(c => continentStruct.cmdsAvailable.contains(c.commandId)),
      activeFuncs = makePrebuiltActives(continentStruct.preBuiltActive, functionIds) ::: listedFunctions
        .filter(_.category == Categories.function)
        .filter(a => if (allowedActivesIds.nonEmpty) allowedActivesIds.contains(a.created_id) else false),
      stagedFunctions = makeAssignedStaged(continentStruct.assignedStaged, functionIds) ::: listedFunctions
        .filter(_.category == Categories.staged)
        .filter(s => if (allowedActivesIds.nonEmpty) allowedActivesIds.contains(s.created_id) else false)
        .take(continentStruct.stagedQty)
    )
  }
}

case class PreparedFunctions(
    main: Function,
    cmds: List[Function],
    activeFuncs: List[Function],
    stagedFunctions: List[Function]
)
