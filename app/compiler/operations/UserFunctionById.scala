package compiler.operations

class UserFunctionById(operations: Seq[Operation], val created_id : String) extends UserFunction(operations)

object UserFunctionById {
  def apply(operations : Seq[Operation] = Seq.empty[Operation], created_id : String) = new UserFunctionById(operations, created_id)

  def unapply(arg : UserFunctionById) : Option[(Seq[Operation], String)] = Some((arg.operations, arg.created_id))
}