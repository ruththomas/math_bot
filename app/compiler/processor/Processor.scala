package compiler.processor

import compiler.operations.{ ChangeRobotDirection, MoveRobotForwardOneSpot, _ }
import compiler.{ Grid, GridAndProgram }
import configuration.CompilerConfiguration

import scala.annotation.tailrec

class Processor(val initialGridAndProgram: GridAndProgram, config : CompilerConfiguration) {

  private class ProcessorState(
      var currentRegister: Register,
      var currentGrid: Grid
  )

  def execute(): Stream[Frame] = {
    val state = new ProcessorState(Register(), initialGridAndProgram.grid)
    execute(state, Some(initialGridAndProgram.program), Stream.empty[Operation], 0)
  }

  private def passColorCheck(operation: Operation, state: ProcessorState): Boolean = true

  @tailrec
  private def execute(state: ProcessorState, maybeOperation: Option[Operation], post: Seq[Operation], emptyLoopCount : Int): Stream[Frame] = {
    if (emptyLoopCount > config.maxEmptyLoopCount) {
      Stream.empty[Frame] // End of program
    } else {

      maybeOperation match {
        case Some(operation) if passColorCheck(operation, state) =>
          operation match {
            case UserFunction(operations) =>
                execute(state, operations.headOption, operations.tail ++: post, emptyLoopCount + 1)
            case IfColor(color, conditionalOperation) =>
              state.currentRegister.peek() match {
                case Some(element) if element.color == color =>
                  execute(state, Some(conditionalOperation), post, emptyLoopCount + 1)
                case Some(_) if color == "grey" =>
                  execute(state, Some(conditionalOperation), post, emptyLoopCount + 1)
                case _ =>
                  execute(state, post.headOption, post.drop(1), emptyLoopCount + 1) // Skip the operation inside the if
              }
            case _ =>
              // Execute the operation
              executeHelp(state, process(state, operation), post.headOption, post.drop(1))
          }
        case _ =>
          if (post.isEmpty)
            Stream.empty[Frame] // End of program
          else
            execute(state, post.headOption, post.drop(1), emptyLoopCount + 1) // Skip the current operation
      }
    }
  }

  private def executeHelp(state: ProcessorState,
                          executed: Frame,
                          maybeOperation: Option[Operation],
                          post: Seq[Operation]): Stream[Frame] = {
    executed #:: execute(state, maybeOperation, post, 0)
  }

  private def process(state: ProcessorState, operation: Operation): Frame = operation match {

    case PickUpItem =>
      val (grid, change, item) = state.currentGrid.pickupItem()
      state.currentRegister = state.currentRegister.push(item)
      state.currentGrid = grid
      Frame(operation, state.currentRegister, grid, Some(state.currentGrid.getRobotLocation), change)

    case SetItemDown =>
      state.currentRegister.pop() match {
        case Some((register, element)) =>
          val (grid, change) = state.currentGrid.setItemDown(element)
          state.currentRegister = register.clearAnimation()
          state.currentGrid = grid
          Frame(operation,
                state.currentRegister,
                state.currentGrid,
                Some(state.currentGrid.getRobotLocation),
                Some(change))
        case None =>
          state.currentRegister = state.currentRegister.clearAnimation()
          Frame(operation, state.currentRegister, state.currentGrid, Some(state.currentGrid.getRobotLocation), None)
      }

    case ChangeRobotDirection =>
      state.currentGrid = state.currentGrid.changeDirection()
      state.currentRegister = state.currentRegister.clearAnimation()
      Frame(operation, state.currentRegister, state.currentGrid, Some(state.currentGrid.getRobotLocation))

    case MoveRobotForwardOneSpot =>
      state.currentGrid.moveRobotForwardOneSpot() match {
        case Some(grid) =>
          state.currentGrid = grid
          state.currentRegister = state.currentRegister.clearAnimation()
          Frame(operation, state.currentRegister, state.currentGrid, Some(state.currentGrid.getRobotLocation))
        case None =>
          state.currentRegister = state.currentRegister.copy(animation = Some(AnimationType.Bumped))
          Frame(operation, state.currentRegister, state.currentGrid, Some(state.currentGrid.getRobotLocation))
      }

    case _ => Frame(operation, state.currentRegister, state.currentGrid)
  }
}

object Processor {
  def apply(initialGridAndProgram: GridAndProgram, config: CompilerConfiguration): Processor = new Processor(initialGridAndProgram, config)
}
