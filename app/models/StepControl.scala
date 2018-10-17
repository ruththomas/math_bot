package models

import actors.messages.RawStepData
import compiler.CellType
import compiler.processor.Frame

object StepControl {
  private def isFinalSpot(frame: Frame) =
    frame.board.currentCell().cellType == CellType.FinalAnswer

  private def totalDropped(frame: Frame, problem: Problem) = {
    val spotSum = frame.board.currentCell().contents.map(_.value).sum
    val solution = Problem.evalProblem(problem)
    spotSum == solution.toInt
  }

  private def discoverRecursion(main: FuncToken,
                                actives: List[FuncToken],
                                visited: Set[FuncToken] = Set.empty[FuncToken]): Boolean = {
    visited.contains(main) match {
      case true =>
        true
      case false =>
        main.func match {
          case Some(contents) =>
            contents
              .flatMap(func => actives.find(_.created_id == func.created_id))
              .exists(func => discoverRecursion(func, actives, visited + main))
          case None =>
            false
        }
    }
  }

  def checkParams(parameters: List[String], activeFuncs: List[FuncToken], lambdas: Lambdas): Boolean = {
    val main: FuncToken = lambdas.main
    val actives: List[FuncToken] = lambdas.activeFuncs
    parameters.foldLeft(true) { (bool, param) =>
      if (bool) {
        param match {
          case "functionRequired" =>
            main.func.getOrElse(List.empty[FuncToken]).exists { ft =>
              activeFuncs.find(f => f.created_id == ft.created_id) match {
                case Some(funcToken) => funcToken.`type`.contains("function")
                case None => false
              }
            }
          case "recursionRequired" =>
            discoverRecursion(main, actives)
          case _ => true
        }
      } else bool
    }
  }
}

class StepControl(parameters: List[String], description: String, lambdas: Lambdas) {

  import StepControl._

  def success(frame: Frame, problem: Problem): Boolean = {
    isFinalSpot(frame) && totalDropped(frame, problem) && checkParams(parameters, lambdas.activeFuncs, lambdas)
  }
}
