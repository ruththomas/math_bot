package compiler.processor

import compiler.operations._
import compiler.{Grid, GridAndProgram}

import scala.annotation.tailrec

class Processor(val initialGridAndProgram: GridAndProgram) {

  private class ProcessorState(
      var currentRegister: Register,
      var currentGrid: Grid
  )

  def execute(): Stream[Frame] = {
    val state = new ProcessorState(Register(), initialGridAndProgram.grid)
    execute(state, Some(initialGridAndProgram.program), Stream.empty[Operation])
  }

  private def passColorCheck(operation: Operation, state: ProcessorState): Boolean =
    (operation.getColor(), state.currentRegister.holdingCell.contents.headOption.map(v => v.color)) match {
      case (Some("grey"), Some(_)) => true
      case (Some(opColor), Some(botColor)) => opColor == botColor
      case (Some(_), None) => false
      case (None, _) => true
    }

  @tailrec
  private def execute(state: ProcessorState, maybeOperation: Option[Operation], post: Seq[Operation]): Stream[Frame] = {
    maybeOperation match {
      case Some(operation) if passColorCheck(operation, state) =>
        operation match {
          case Program(operations) =>
            // Execute the main program.
            // Note: smh69 observed Program and UserFunction are practically the same, and they are.
            // This is more than likely temporary, but for now its because the UI treats them differently
            // because they display differently.
            execute(state, operations.headOption, operations.tail ++: post)
          case UserFunction(operations) =>
            if (operations.length == 1 && operations.head.isInstanceOf[UserFunction])
              // Skip functions that only call another function to avoid non-existing function call loop (not best solution)
              execute(state, post.headOption, post.drop(1))
            else
              // Insert the function into the operations stream
              execute(state, operations.headOption, operations.tail ++: post)
          case _ =>
            // Execute the operation
            executeHelp(state, process(state, operation), post.headOption, post.drop(1))
        }
      case _ =>
        if (post.isEmpty)
          Stream.empty[Frame] // End of program
        else
          execute(state, post.headOption, post.drop(1)) // Skip the current operation
    }
  }

  private def executeHelp(state: ProcessorState,
                          executed: Frame,
                          maybeOperation: Option[Operation],
                          post: Seq[Operation]): Stream[Frame] = {
    executed #:: execute(state, maybeOperation, post)
  }

  private def process(state: ProcessorState, operation: Operation): Frame = operation match {

    case Program(_) =>
      state.currentRegister = Register()
      Frame(operation, state.currentRegister, state.currentGrid)

    case PickUpItem(_) =>
      val (grid, change, item) = state.currentGrid.pickupItem()
      state.currentRegister = state.currentRegister.push(item)
      state.currentGrid = grid
      Frame(operation, state.currentRegister, grid, Some(state.currentGrid.getRobotLocation), change)

    case SetItemDown(_) =>
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

    case ChangeRobotDirection(_) =>
      state.currentGrid = state.currentGrid.changeDirection()
      state.currentRegister = state.currentRegister.clearAnimation()
      Frame(operation, state.currentRegister, state.currentGrid, Some(state.currentGrid.getRobotLocation))

    case MoveRobotForwardOneSpot(_) =>
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
  def apply(initialGridAndProgram: GridAndProgram): Processor = new Processor(initialGridAndProgram)
}
