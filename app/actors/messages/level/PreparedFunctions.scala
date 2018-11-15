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
          category = Categories.staged,
          sizeLimit = makeQtyUnlimited(as.sizeLimit)
        )
      }
  }

  def makePrebuiltActives(prebuiltActives: List[AssignedFunction],
                          cmds: List[Function],
                          functionIds: List[String]): List[Function] = {
    prebuiltActives
      .filterNot { pa =>
        functionIds.contains(pa.createdId)
      }
      .map { pa =>
        Function(
          created_id = pa.createdId,
          func = Some(pa.func.flatMap(fn => cmds.find(_.commandId.contains(fn)))),
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
    val preBuiltActives = makePrebuiltActives(continentStruct.preBuiltActive,
                                              listedFunctions.filter(_.category == Categories.command),
                                              functionIds)
    val assignedStaged = makeAssignedStaged(continentStruct.assignedStaged, functionIds)

    def isAllowedActive(func: Function) = continentStruct.allowedActives match {
      case Some(allowed) if allowed.nonEmpty => allowed.contains(func.created_id)
      case Some(allowed) if allowed.isEmpty => false
      case None => true
    }

    new PreparedFunctions(
      main = listedFunctions
        .filter(f => f.category == Categories.main)
        .map { m =>
          m.copy(
            func = m.func.map {
              _.take(continentStruct.maxMain).filter(
                f => f.category == Categories.command || (f.category != Categories.command && isAllowedActive(f))
              )
            }
          )
        }
        .head,
      cmds = listedFunctions
        .filter(c => c.category == Categories.command)
        .filter(c => continentStruct.cmdsAvailable.contains(c.commandId))
        .sortBy(_.index),
      activeFuncs = listedFunctions
        .filter(_.category == Categories.function)
        .filter(isAllowedActive) ::: preBuiltActives
        .sortBy(_.index),
      stagedFunctions = listedFunctions
        .filter(_.category == Categories.staged)
        .filter(isAllowedActive) ::: assignedStaged
        .sortBy(_.index)
    )
  }
}

case class PreparedFunctions(
    main: Function,
    cmds: List[Function],
    activeFuncs: List[Function],
    stagedFunctions: List[Function]
)
