package compiler.operations

case class UserFunctionRefById(created_id : String) extends UserFunctionRef {
  override def toString = s"UserFunctionRefById($created_id)"
}
