package compiler.processor

import compiler.operations.{ ChangeRobotDirection, MoveRobotForwardOneSpot, _ }
import compiler.{ ElementKinds, Grid, GridAndProgram }
import configuration.CompilerConfiguration

import scala.annotation.tailrec

class Processor(val initialGridAndProgram: GridAndProgram, config: CompilerConfiguration) {

  private class ProcessorState(
      var currentRegister: Register,
      var currentGrid: Grid
  )

  def execute(successCheck : Grid => Boolean, checkEveryFrame : Boolean): Stream[Frame] = {
    val state = new ProcessorState(Register(), initialGridAndProgram.grid)
    val trace = initialGridAndProgram.program match {
      case uf: UserFunction => TraceTag(uf, 0)
    }
    execute(state, Some((Initial, TraceTag(UserFunction(), -1))), Seq((initialGridAndProgram.program, trace)), 0, 0, successCheck, checkEveryFrame)
  }

  @tailrec
  private def execute(state : ProcessorState, maybeOperation : Option[(Operation, TraceTag)], post : Seq[(Operation, TraceTag)], stepCount : Int, emptyLoopCount : Int, successCheck : Grid => Boolean, checkEveryFrame : Boolean) : Stream[Frame] = {
    if (emptyLoopCount > config.maxEmptyLoopCount || stepCount > config.maxProgramSteps) {
      Stream(generateFinalFrame(state, stepCount, isSuccess = false))
    } else {
      maybeOperation match {
        case Some(operation) =>
          operation._1 match {
            case uf: UserFunction =>
              val withTrace = uf.operations.zipWithIndex.map(t => (t._1, TraceTag(uf, t._2)))
              execute(state, withTrace.headOption, withTrace.tail ++: post, stepCount, emptyLoopCount + 1, successCheck, checkEveryFrame)
            case IfElement(color, conditionalOperation) =>
              state.currentRegister.peek() match {
                case Some(element) if element.color == color =>
                  execute(state, Some((conditionalOperation, operation._2)), post, stepCount, emptyLoopCount + 1, successCheck, checkEveryFrame)
                case Some(_) if color == ElementKinds.anyColor =>
                  execute(state, Some((conditionalOperation, operation._2)), post, stepCount, emptyLoopCount + 1, successCheck, checkEveryFrame)
                case None if color == ElementKinds.emptyColor =>
                  execute(state, Some((conditionalOperation, operation._2)), post, stepCount, emptyLoopCount + 1, successCheck, checkEveryFrame)
                case _ =>
                  execute(state, post.headOption, post.drop(1), stepCount, emptyLoopCount + 1, successCheck, checkEveryFrame) // Skip the operation inside the if
              }
            case _ =>
              // Execute the operation
              if (checkEveryFrame)
                executeHelp(state, process(state, stepCount, operation, successCheck), stepCount , post.headOption, post.drop(1), successCheck, checkEveryFrame)
              else
                executeHelp(state, process(state, stepCount, operation, _ => false), stepCount , post.headOption, post.drop(1), successCheck, checkEveryFrame)

          }
        case _ =>
          if (post.isEmpty) {
            Stream(generateFinalFrame(state, stepCount, successCheck(state.currentGrid)))
          }
          else
            execute(state, post.headOption, post.drop(1), stepCount, emptyLoopCount + 1, successCheck, checkEveryFrame) // Skip the current operation
      }
    }
  }

  private def generateFinalFrame(state : ProcessorState, frameIndex: Int, isSuccess : Boolean) : Frame =
    Frame(Final, state.currentRegister, state.currentGrid, TraceTag(UserFunction(), -1), Some(state.currentGrid.getRobotLocation), None, isSuccess, index = Some(frameIndex))

  private def executeHelp(state : ProcessorState, executed : Frame, stepCount : Int, maybeOperation : Option[(Operation, TraceTag)], post : Seq[(Operation, TraceTag)], successCheck : Grid => Boolean, checkEveryFrame : Boolean) = {
    if (executed.success)
      Stream(executed, generateFinalFrame(state, stepCount, executed.success))
    else
      executed #:: execute(state, maybeOperation, post, stepCount + 1 , 0, successCheck, checkEveryFrame)
  }

  private def process(state: ProcessorState, frameIndex : Int, operation: (Operation, TraceTag),
                      successCheck : Grid => Boolean): Frame =
    operation._1 match {

      case PickUpItem =>
        val (grid, change, item) = state.currentGrid.pickupItem()
        state.currentRegister = state.currentRegister.push(item)
        state.currentGrid = grid
        Frame(operation._1, state.currentRegister, grid, operation._2, Some(state.currentGrid.getRobotLocation), change, successCheck(state.currentGrid), index = Some(frameIndex))

      case SetItemDown =>
        state.currentRegister.pop() match {
          case Some((register, element)) =>
            val (grid, change) = state.currentGrid.setItemDown(element)
            state.currentRegister = register.clearAnimation()
            state.currentGrid = grid
            Frame(operation._1,
                  state.currentRegister,
                  state.currentGrid,
                  operation._2,
                  Some(state.currentGrid.getRobotLocation),
                  Some(change), successCheck(state.currentGrid),
                  index = Some(frameIndex)
            )
          case None =>
            state.currentRegister = state.currentRegister.clearAnimation()
            Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, Some(state.currentGrid.getRobotLocation), None, successCheck(state.currentGrid), index = Some(frameIndex))
        }

      case ChangeRobotDirection =>
        state.currentGrid = state.currentGrid.changeDirection()
        state.currentRegister = state.currentRegister.clearAnimation()
        Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, Some(state.currentGrid.getRobotLocation), None, successCheck(state.currentGrid), index = Some(frameIndex))

      case MoveRobotForwardOneSpot =>
        state.currentGrid.moveRobotForwardOneSpot() match {
          case Some(grid) =>
            state.currentGrid = grid
            state.currentRegister = state.currentRegister.clearAnimation()
            Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, Some(state.currentGrid.getRobotLocation), None, successCheck(state.currentGrid), index = Some(frameIndex))
          case None =>
            state.currentRegister = state.currentRegister.copy(animation = Some(AnimationType.Bumped))
            Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, Some(state.currentGrid.getRobotLocation), None, successCheck(state.currentGrid), index = Some(frameIndex))
        }

      case Initial =>
        Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, Some(state.currentGrid.getRobotLocation), index = Some(frameIndex))

      case _ => Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, None, None, successCheck(state.currentGrid), index = Some(frameIndex))
    }
}

object Processor {
  def apply(initialGridAndProgram: GridAndProgram, config: CompilerConfiguration): Processor =
    new Processor(initialGridAndProgram, config)
}
