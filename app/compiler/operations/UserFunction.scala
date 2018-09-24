package compiler.operations

class UserFunction(var operations: Seq[Operation] = Seq.empty[Operation]) extends Operation {
  override def toString = s"UserFunction"
}

object UserFunction {
  def unapply(arg: UserFunction): Option[Seq[Operation]] = Some(arg.operations)
}
