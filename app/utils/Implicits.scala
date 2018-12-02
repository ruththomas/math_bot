package utils
import scala.language.implicitConversions

object Implicits {
  implicit def mbString(str: String): MbString = new MbString(str)
}
