package compiler

import actors.messages.level.Problem
import compiler.operations.Operation
case class GridAndProgram(grid: Grid, program: Operation, problem: Problem)
