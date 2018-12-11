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

  def execute(): Stream[Frame] = {
    val state = new ProcessorState(Register(), initialGridAndProgram.grid)
    val trace = initialGridAndProgram.program match {
      case uf: UserFunction => TraceTag(uf, 0)
    }
    execute(state, Some((initialGridAndProgram.program, trace)), Seq.empty[(Operation, TraceTag)], 0)
  }

  @tailrec
  private def execute(state: ProcessorState,
                      maybeOperation: Option[(Operation, TraceTag)],
                      post: Seq[(Operation, TraceTag)],
                      emptyLoopCount: Int): Stream[Frame] = {
    if (emptyLoopCount > config.maxEmptyLoopCount) {
      Stream.empty[Frame] // End of program
    } else {
      maybeOperation match {
        case Some(operation) =>
          operation._1 match {
            case uf: UserFunction =>
              val withTrace = uf.operations.zipWithIndex.map(t => (t._1, TraceTag(uf, t._2)))
              execute(state, withTrace.headOption, withTrace.tail ++: post, emptyLoopCount + 1)
            case IfElement(color, conditionalOperation) =>
              state.currentRegister.peek() match {
                case Some(element) if element.color == color =>
                  execute(state, Some((conditionalOperation, operation._2)), post, emptyLoopCount + 1)
                case Some(_) if color == ElementKinds.anyColor =>
                  execute(state, Some((conditionalOperation, operation._2)), post, emptyLoopCount + 1)
                case None if color == ElementKinds.emptyColor =>
                  execute(state, Some((conditionalOperation, operation._2)), post, emptyLoopCount + 1)
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
                          maybeOperation: Option[(Operation, TraceTag)],
                          post: Seq[(Operation, TraceTag)]): Stream[Frame] = {
    executed #:: execute(state, maybeOperation, post, 0)
  }

  private def process(state: ProcessorState, operation: (Operation, TraceTag)): Frame =
    operation._1 match {

      case PickUpItem =>
        val (grid, change, item) = state.currentGrid.pickupItem()
        state.currentRegister = state.currentRegister.push(item)
        state.currentGrid = grid
        Frame(operation._1, state.currentRegister, grid, operation._2, Some(state.currentGrid.getRobotLocation), change)

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
                  Some(change))
          case None =>
            state.currentRegister = state.currentRegister.clearAnimation()
            Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, Some(state.currentGrid.getRobotLocation), None)
        }

      case ChangeRobotDirection =>
        state.currentGrid = state.currentGrid.changeDirection()
        state.currentRegister = state.currentRegister.clearAnimation()
        Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, Some(state.currentGrid.getRobotLocation))

      case MoveRobotForwardOneSpot =>
        state.currentGrid.moveRobotForwardOneSpot() match {
          case Some(grid) =>
            state.currentGrid = grid
            state.currentRegister = state.currentRegister.clearAnimation()
            Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, Some(state.currentGrid.getRobotLocation))
          case None =>
            state.currentRegister = state.currentRegister.copy(animation = Some(AnimationType.Bumped))
            Frame(operation._1, state.currentRegister, state.currentGrid, operation._2, Some(state.currentGrid.getRobotLocation))
        }

      case _ => Frame(operation._1, state.currentRegister, state.currentGrid, operation._2)
    }
}

object Processor {
  def apply(initialGridAndProgram: GridAndProgram, config: CompilerConfiguration): Processor =
    new Processor(initialGridAndProgram, config)
}
