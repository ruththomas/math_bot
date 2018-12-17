package models

import actors.messages.level.{ GridPart, Problem, ToolList }
import _root_.compiler.Grid

/*
 * Only used in compiler.
 * */
case class GridMap(
    gMap: List[List[GridPart]],
    robotOrientation: String,
    success: (Grid, Problem) => Boolean,
    problem: Problem,
    evalEachFrame: Boolean,
    description: String,
    toolList: List[ClientElement]
)
