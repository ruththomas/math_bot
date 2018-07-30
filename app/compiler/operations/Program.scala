package compiler.operations

class Program(var operations: Seq[Operation] = Seq.empty[Operation]) extends Operation {
}

object Program {
  def unapply(arg: Program): Option[Seq[Operation]] = Some(arg.operations)
}