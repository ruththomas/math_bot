package compiler.operations

class UserFunction(var operations: Seq[Operation]) extends Operation {
  override def toString = s"UserFunction"
}

object UserFunction {
  def apply(operations: Seq[Operation] = Seq.empty[Operation]) = new UserFunction(operations)
  def unapply(arg: UserFunction): Option[Seq[Operation]] = Some(arg.operations)
}
