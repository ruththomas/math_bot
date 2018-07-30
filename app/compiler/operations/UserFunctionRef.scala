package compiler.operations

case class UserFunctionRef(val created_id: String) extends UserFunction(Seq.empty[Operation]) {
  override def toString = s"UserFunctionRef($created_id)"
}
