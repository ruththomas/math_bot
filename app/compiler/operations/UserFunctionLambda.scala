package compiler.operations

class UserFunctionLambda(operations : Seq[Operation], var id : Int) extends UserFunction(operations)

object UserFunctionLambda {
  def apply(operations : Seq[Operation] = Seq.empty[Operation], id : Int = 0) = new UserFunctionLambda(operations, id)

  def unapply(arg : UserFunctionLambda) : Option[(Seq[Operation], Int)] = Some((arg.operations, arg.id))
}
