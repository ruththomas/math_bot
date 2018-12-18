package models

import _root_.compiler.Grid
import actors.messages.level.{ClientElement, GridPart, Problem}

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
