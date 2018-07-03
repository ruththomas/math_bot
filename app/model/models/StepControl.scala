package model.models

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
            main.func.getOrElse(List.empty[FuncToken]).exists { ft =>
              actives.find(_.created_id == ft.created_id) match {
                case Some(fullFunc) =>
                  fullFunc.func.getOrElse(List.empty[FuncToken]).exists(_.created_id == ft.created_id)
                case None =>
                  false
              }
            }
          case _ => true
        }
      } else bool
    }
  }
}

class StepControl(rawStepData: RawStepData, lambdas: Lambdas) {

  import StepControl._

  val parameters: List[String] = rawStepData.specialParameters
  val description: String = rawStepData.description
  val activeFuncs: List[FuncToken] = lambdas.activeFuncs

  def success(frame: Frame, problem: Problem): Boolean = {
    val finalSpotBool = isFinalSpot(frame)
    val totalDroppedBool = totalDropped(frame, problem)
    val specialParamsBool = checkParams(parameters, activeFuncs, lambdas)

    finalSpotBool && totalDroppedBool && specialParamsBool
  }
}
