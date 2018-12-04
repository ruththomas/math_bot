package compiler.operations

import compiler.Colors

case class IfColor(color : Colors.Value, operation: Operation) extends Operation
