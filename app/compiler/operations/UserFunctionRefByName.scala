package compiler.operations

case class UserFunctionRefByName(val name : String) extends UserFunctionRef {
  override def toString = s"UserFunctionRefByName($name)"
}
