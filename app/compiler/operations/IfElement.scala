package compiler.operations

import compiler.ElementKinds

case class IfElement(color : ElementKinds.Value, operation : Operation) extends Operation
