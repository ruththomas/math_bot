package utils
import scala.util.Try

class MbString(str: String) {
  def toIntOpt: Option[Int] = Try(str.toInt).toOption
}
