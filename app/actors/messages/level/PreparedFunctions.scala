package actors.messages.level
import actors.LevelGenerationActor.makeQtyUnlimited
import actors.messages.AssignedFunction
import level_gen.models.ContinentStruct
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

  private def indexEm(functions: List[Function]): List[Function] =
    functions.zipWithIndex.map(fNi => fNi._1.copy(index = fNi._2))

  def apply(functions: Functions, continentStruct: ContinentStruct): PreparedFunctions = {
    val listedFunctions = functions.listed.sortBy(_.index)
    val functionIds = getFunctionIds(listedFunctions)
    val preBuiltActives = makePrebuiltActives(continentStruct.preBuiltActive,
                                              listedFunctions.filter(_.category == Categories.command),
                                              functionIds)
    val assignedStaged = makeAssignedStaged(continentStruct.assignedStaged, functionIds)
    val main = listedFunctions.filter(_.category == Categories.main).head
    val cmds = listedFunctions.filter(_.category == Categories.command)
    val actives = indexEm(preBuiltActives ::: listedFunctions.filter(_.category == Categories.function))
    val staged = indexEm(assignedStaged ::: listedFunctions.filter(_.category == Categories.staged))

    def isAllowedActive(func: Function) = continentStruct.allowedActives match {
      case Some(allowed) if allowed.nonEmpty => allowed.contains(func.created_id)
      case Some(allowed) if allowed.isEmpty => false
      case None => true
    }

    new PreparedFunctions(
      main = main.copy(
        func = main.func.map {
          _.take(continentStruct.maxMain)
            .filter(
              f => f.category == Categories.command || (f.category != Categories.command && isAllowedActive(f))
            )
        }
      ),
      cmds = cmds.filter(c => continentStruct.cmdsAvailable.contains(c.commandId)),
      activeFuncs = actives.filter(isAllowedActive),
      stagedFunctions = staged.filter(isAllowedActive)
    )
  }
}

case class PreparedFunctions(
    main: Function,
    cmds: List[Function],
    activeFuncs: List[Function],
    stagedFunctions: List[Function]
)
