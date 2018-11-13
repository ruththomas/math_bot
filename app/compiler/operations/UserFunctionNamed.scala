package compiler.operations

class UserFunctionNamed(operations: Seq[Operation], val name: String) extends UserFunction(operations)

object UserFunctionNamed {
  def apply(operations : Seq[Operation] = Seq.empty[Operation], name : String) = new UserFunctionNamed(operations, name)

  def unapply(arg : UserFunctionNamed) : Option[(Seq[Operation], String)] = Some((arg.operations, arg.name))
}