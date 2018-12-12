package compiler.processor

import compiler.{ Cell, CellChange, Grid }
import compiler.operations.{ NoOperation, Operation, UserFunctionById }

case class Frame(operation: Operation, register: Register, board: Grid, traceTag: TraceTag, robotLocation: Option[RobotLocation] = None, cellChange : Option[CellChange] = None,
                 success : Boolean = false) {
  def withMinimalGrid : Frame =
    cellChange match {
      case Some(cell) =>
        this.copy(board = board.applyChangeCell(cell))
      case _ => this.copy(board = board.copy(grid = Map.empty[(Int, Int), Cell]))
    }
}

object Frame {
  def nopFrame(board : Grid) : Frame = Frame(NoOperation, Register(), board, TraceTag(UserFunctionById(created_id = "10000"), 0), None, None)
}