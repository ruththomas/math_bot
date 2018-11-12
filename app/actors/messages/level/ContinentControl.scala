package actors.messages.level
import compiler.CellType
import compiler.processor.Frame
import models.Problem

object ContinentControl {
  private def isFinalSpot(frame: Frame) =
    frame.board.currentCell().cellType == CellType.FinalAnswer

  private def totalDropped(frame: Frame, problem: Problem) = {
    val spotSum = frame.board.currentCell().contents.map(_.value).sum
    val solution = Problem.evalProblem(problem)
    spotSum == solution.toInt
  }

  private def discoverRecursion(main: Function,
                                actives: List[Function],
                                visited: Set[Function] = Set.empty[Function]): Boolean = {
    visited.contains(main) match {
      case true =>
        true
      case false =>
        main.func.exists(
          _.flatMap(func => actives.find(_.created_id == func.created_id))
            .exists(func => discoverRecursion(func, actives, visited + main))
        )
    }
  }

  def checkParams(parameters: List[String], functions: List[Function]): Boolean = {
    val main: Function = functions.filter(_.category == Categories.main).head
    val actives: List[Function] = functions.filter(_.category == Categories.function)
    parameters.foldLeft(true) { (bool, param) =>
      if (bool) {
        param match {
          case "functionRequired" =>
            main.func.forall(_.exists { ft =>
              actives.find(f => f.created_id == ft.created_id) match {
                case Some(funcToken) => funcToken.category.contains(Categories.function)
                case None => false
              }
            })
          case "recursionRequired" =>
            discoverRecursion(main, actives)
          case _ => true
        }
      } else bool
    }
  }
}

class ContinentControl(parameters: List[String], description: String, functions: Functions) {
  import ContinentControl._
  val listedFunctions = functions.list.values.toList

  def success(frame: Frame, problem: Problem): Boolean = {
    isFinalSpot(frame) && totalDropped(frame, problem) && checkParams(parameters, listedFunctions)
  }
}