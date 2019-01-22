package compiler.processor

import compiler.operations.Operation
import compiler.{ Cell, CellChange, Grid }

case class Frame(operation: Operation, register: Register, board: Grid, change: FrameChange, traceTag: TraceTag, index: Int, cellChange: Option[CellChange] = None, success: Boolean = false) {
  def withMinimalGrid : Frame =
    cellChange match {
      case Some(cell) =>
        this.copy(board = board.applyChangeCell(cell))
      case _ => this.copy(board = board.copy(grid = Map.empty[(Int, Int), Cell]))
    }
}
