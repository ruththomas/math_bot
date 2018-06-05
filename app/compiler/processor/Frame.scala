package compiler.processor

import compiler.{ CellChange, Grid }
import compiler.operations.Operation

case class Frame(operation: Operation, register: Register, board: Grid, robotLocation: Option[RobotLocation] = None, cellChange : Option[CellChange] = None) {
  def withMinimalGrid() =
    cellChange match {
      case Some(cell) =>
        this.copy(board = board.applyChangeCell(cell))
      case _ => this
    }
}
