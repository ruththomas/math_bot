package actors.messages.level
import actors.messages.AssignedFunction
import daos.FunctionsDAO
import level_gen.models.ContinentStruct
import play.api.libs.json.{Json, OFormat}
import utils.Implicits._

object PreparedFunctions {
  import compiler.ElementKinds._
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
    functions.zipWithIndex.map(fNi => fNi._1.copy(index = fNi._2, sizeLimit = fNi._1.clientSizeLimit)).sortBy(_.index)

  private def isAllowedFunction(func: Function, continentStruct: ContinentStruct): Boolean =
    (if (func.category == Categories.function || func.category == Categories.staged) continentStruct.allowedActives
     else continentStruct.cmdsAvailable) match {
      case Some(allowed) if allowed.nonEmpty => allowed.contains(func.created_id)
      case Some(allowed) if allowed.isEmpty => false
      case None => true
    }

  private object FilteredFunctions {
    def apply(
        main: Function,
        actives: List[Function],
        staged: List[Function],
        continentStruct: ContinentStruct
    ): FilteredFunctions =
      new FilteredFunctions(
        main = main.copy(func = main.func.map {
          _.take(continentStruct.maxMain)
            .filter(
              f =>
                f.category == Categories.command || (f.category != Categories.command && isAllowedFunction(
                  f,
                  continentStruct
                ))
            )
        }),
        actives = actives.filter(isAllowedFunction(_, continentStruct)).sortBy(_.index),
        staged = staged.filter(isAllowedFunction(_, continentStruct)).sortBy(_.index)
      )
  }

  private case class FilteredFunctions(main: Function, actives: List[Function], staged: List[Function])

  private def addMoreStaged(actives: List[Function], staged: List[Function]): List[Function] = {
    if (staged.length > 50) staged
    else {
      val imageName =
      (actives ::: staged).filter(_.created_id.startsWith("2")).flatMap(_.image.toIntOpt).max + 1
      val newStaged = DefaultFunctions.funcs.head.copy(
        created_id = s"200000${imageName.toString}",
        image = imageName.toString,
        name = if (imageName > 101) s"Name me ${imageName.toString}" else "",
        displayName = Some(imageName > 101)
      )
      addMoreStaged(actives, staged :+ newStaged)
    }
  }

  private def trimStaged(staged: List[Function]): List[Function] = {
    if (staged.length < 50) staged
    else {
      val toRemove = staged.filter(f => f.created_id.startsWith("2")).flatMap(_.image.toIntOpt).max
      trimStaged(staged.filterNot(_.image == toRemove.toString))
    }
  }

  /*
   * Converts any color not found in color pallet to white
   * As long as the color `white` is found in `compiler/ElementKinds.scala`
   * this function will revert function colors that aren't otherwise
   * found in ElementKinds.scala
   * */
  private def convertColors(functions: List[Function]): List[Function] = functions.map { f =>
    f.copy(
      color = all.find(_.name == f.color).getOrElse(white).name,
      func = f.func.map(convertColors)
    )
  }

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
    val actives = functions.actives
    val staged = functions.staged

    val filteredFunctions = FilteredFunctions(functions.main, actives, staged, continentStruct)

    if ((function.category == Categories.command || function.category == Categories.function) && actives.exists(
          _.created_id == function.created_id
        )) {
      // todo - verify if this block is actually needed.
      // reposition active function
      val insertAt: Int = filteredFunctions.actives(function.index).index
      val activesWithOutFunction = actives.filterNot(_.created_id == function.created_id)
      val updatedActives = indexEm(
        activesWithOutFunction.take(insertAt) ::: List(function) ::: activesWithOutFunction.drop(insertAt)
      )

      val finished = FilteredFunctions(functions.main, updatedActives, functions.staged, continentStruct)

      functionsDAO.replaceAll(
        functions.tokenId,
        Functions(
          functions.tokenId,
          List(functions.main) ::: updatedActives ::: functions.staged
        )
      )

      new PreparedFunctions(
        main = finished.main,
        activeFunctions = finished.actives,
        stagedFunctions = finished.staged
      )
    } else {
      // activate/deactivate function
      val finished = function.category match {
        case Categories.function => // activate function
          val insertAt: Int = {
            if (filteredFunctions.actives.isEmpty) 0
            else if (filteredFunctions.actives.length <= function.index)
              filteredFunctions.actives.last.index + 1
            else filteredFunctions.actives(function.index).index
          }
          val updatedActives = indexEm(actives.take(insertAt) ::: List(function) ::: actives.drop(insertAt))
          val updatedStaged = staged.filterNot(_.created_id == function.created_id)

          // if staged functions is less than 50 add a new function
          val newStagedAdded = indexEm(addMoreStaged(updatedActives, updatedStaged))

          functionsDAO.replaceAll(
            functions.tokenId,
            Functions(
              functions.tokenId,
              List(functions.main) ::: updatedActives ::: newStagedAdded
            )
          )
          FilteredFunctions(functions.main, updatedActives, updatedStaged, continentStruct)
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
          val trimmedStaged = trimStaged(staged)
          val updatedStaged = indexEm(trimmedStaged.take(insertAt) ::: List(function) ::: trimmedStaged.drop(insertAt))
          val updatedActives = indexEm(
            filterDeactivated(actives)
              .map(f => f.copy(func = f.func.map(filterDeactivated)))
          )

          functionsDAO.replaceAll(
            functions.tokenId,
            Functions(functions.tokenId, List(updatedMain) ::: updatedActives ::: updatedStaged)
          )
          FilteredFunctions(updatedMain, updatedActives, updatedStaged, continentStruct)
        case _ => // can't be moved
          filteredFunctions
      }
      new PreparedFunctions(finished.main, finished.actives, finished.staged)
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
    val preBuiltActives = makePrebuiltActives(continentStruct.preBuiltActive, functions.commands, functionIds)
    val assignedStaged = makeAssignedStaged(continentStruct.assignedStaged, functionIds)
    val main = functions.main.copy(func = functions.main.func.map(convertColors))
    val actives = indexEm(convertColors(preBuiltActives ::: functions.actives))
    val staged = indexEm(convertColors(addMoreStaged(functions.actives, assignedStaged ::: functions.staged)))

    val filteredFunctions = FilteredFunctions(main, actives, staged, continentStruct)
    // sets mains func to empty if main has a max size
    val adjustedMain = filteredFunctions.main

    functionsDAO.replaceAll(
      functions.tokenId,
      Functions(functions.tokenId, List(adjustedMain) ::: actives ::: staged)
    )

    new PreparedFunctions(
      main = adjustedMain,
      activeFunctions = filteredFunctions.actives,
      stagedFunctions = filteredFunctions.staged
    )
  }
}

case class PreparedFunctions(
    main: Function,
    activeFunctions: List[Function],
    stagedFunctions: List[Function]
) {
  def functions: List[Function] = activeFunctions.filter(_.category == Categories.function)
  def cmds: List[Function] = activeFunctions.filter(_.category == Categories.command)
}
