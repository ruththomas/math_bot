package actors.messages.level
import actors.messages.AssignedFunction
import daos.FunctionsDAO
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
          sizeLimit = as.limit
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
          sizeLimit = pa.limit
        )
      }
  }

  private def indexEm(functions: List[Function]): List[Function] =
    functions.zipWithIndex.map(fNi => fNi._1.copy(index = fNi._2))

  private def isAllowedActive(func: Function, continentStruct: ContinentStruct) = continentStruct.allowedActives match {
    case Some(allowed) if allowed.nonEmpty => allowed.contains(func.created_id)
    case Some(allowed) if allowed.isEmpty => false
    case None => true
  }

  private object FilteredFunctions {
    def apply(
        main: Function,
        cmds: List[Function],
        actives: List[Function],
        staged: List[Function],
        continentStruct: ContinentStruct
    ): FilteredFunctions =
      new FilteredFunctions(
        main = main.copy(func = main.func.map {
          _.take(continentStruct.maxMain)
            .filter(
              f =>
                f.category == Categories.command || (f.category != Categories.command && isAllowedActive(
                  f,
                  continentStruct
                ))
            )
        }),
        cmds = cmds.filter(c => continentStruct.cmdsAvailable.contains(c.commandId)),
        actives = actives.filter(isAllowedActive(_, continentStruct)),
        staged = staged.filter(isAllowedActive(_, continentStruct))
      )
  }

  private case class FilteredFunctions(main: Function,
                                       cmds: List[Function],
                                       actives: List[Function],
                                       staged: List[Function])

  /*
   * For activating or deactivating a function
   * Function index should be the functions new position
   * Function category should be the functions new category
   * Functions are replaced in db with updated list
   * */
  def apply(functions: Functions,
            continentStruct: ContinentStruct,
            function: Function,
            functionsDAO: FunctionsDAO): PreparedFunctions = {
    val actives = functions.actives.sortBy(_.index)
    val staged = functions.staged.sortBy(_.index)
    val cmds = functions.commands.sortBy(_.index)

    val filteredFunctions = FilteredFunctions(functions.main, cmds, actives, staged, continentStruct)

    if (function.category == Categories.function && actives.exists(_.created_id == function.created_id)) {
      // reposition active function
      val insertAt: Int = filteredFunctions.actives(function.index).index
      val activesWithOutFunction = actives.filterNot(_.created_id == function.created_id)
      val updatedActives = indexEm(
        activesWithOutFunction.take(insertAt) ::: List(function) ::: activesWithOutFunction.drop(insertAt)
      )

      val finished = FilteredFunctions(functions.main, cmds, updatedActives, functions.staged, continentStruct)

      functionsDAO.replaceAll(
        functions.tokenId,
        Functions(
          functions.tokenId,
          List(functions.main) ::: cmds ::: updatedActives ::: functions.staged
        )
      )

      new PreparedFunctions(
        finished.main,
        finished.cmds,
        finished.actives,
        finished.staged
      )
    } else {
      // activate function
      val finished = function.category match {
        case Categories.function => // activate function
          val insertAt: Int = {
            if (filteredFunctions.actives.isEmpty) 0
            else if (filteredFunctions.actives.length <= function.index)
              filteredFunctions.actives.last.index + 1
            else filteredFunctions.actives(function.index).index
          }
          val updatedActives = indexEm(actives.take(insertAt) ::: List(function) ::: actives.drop(insertAt))
          val updatedStaged = indexEm(staged.filterNot(_.created_id == function.created_id))
          functionsDAO.replaceAll(
            functions.tokenId,
            Functions(
              functions.tokenId,
              List(functions.main) ::: cmds ::: updatedActives ::: updatedStaged
            )
          )
          FilteredFunctions(functions.main, cmds, updatedActives, updatedStaged, continentStruct)
        case Categories.staged => // deactivate function
          def filterDeactivated(funcs: List[Function]): List[Function] =
            funcs.filterNot(_.created_id == function.created_id)

          val insertAt: Int = {
            if (filteredFunctions.staged.isEmpty) 0
            else if (filteredFunctions.staged.length <= function.index)
              filteredFunctions.staged.last.index + 1
            else filteredFunctions.staged(function.index).index
          }
          val updatedMain =
            functions.main.copy(func = functions.main.func.map(filterDeactivated))
          val updatedStaged = indexEm(staged.take(insertAt) ::: List(function) ::: staged.drop(insertAt))
          val updatedActives = indexEm(
            filterDeactivated(actives)
              .map(f => f.copy(func = f.func.map(filterDeactivated)))
          )
          functionsDAO.replaceAll(
            functions.tokenId,
            Functions(functions.tokenId, List(updatedMain) ::: cmds ::: updatedActives ::: updatedStaged)
          )
          FilteredFunctions(updatedMain, cmds, updatedActives, updatedStaged, continentStruct)
      }
      new PreparedFunctions(finished.main, finished.cmds, finished.actives, finished.staged)
    }
  }

  /*
   * For creating built continent
   * (filtered functions for a specific continent)
   * Functions are updated in db with updated list
   * */
  def apply(functions: Functions, continentStruct: ContinentStruct, functionsDAO: FunctionsDAO): PreparedFunctions = {
    val listedFunctions = functions.listed
    val functionIds = getFunctionIds(listedFunctions)
    val cmds = functions.commands.sortBy(_.index)
    val preBuiltActives = makePrebuiltActives(continentStruct.preBuiltActive, cmds, functionIds)
    val assignedStaged = makeAssignedStaged(continentStruct.assignedStaged, functionIds)
    val main = functions.main
    val actives = indexEm(preBuiltActives ::: functions.actives.sortBy(_.index))
    val staged = indexEm(assignedStaged ::: functions.staged.sortBy(_.index))

    val filteredFunctions = FilteredFunctions(main, cmds, actives, staged, continentStruct)

    functionsDAO.replaceAll(functions.tokenId,
                            Functions(functions.tokenId, List(filteredFunctions.main) ::: cmds ::: actives ::: staged))

    new PreparedFunctions(
      main = filteredFunctions.main,
      cmds = filteredFunctions.cmds,
      activeFuncs = filteredFunctions.actives,
      stagedFunctions = filteredFunctions.staged
    )
  }
}

case class PreparedFunctions(
    main: Function,
    cmds: List[Function],
    activeFuncs: List[Function],
    stagedFunctions: List[Function]
)
