package compiler

object Colors extends Enumeration {
  protected case class Color(name: String) extends super.Val {
    override def toString(): String = name
  }

  def from(color: String): Option[Value] = Colors.allColors.find(c => c.name == color)

  val anyColor = Color("any")
  val emptyColor = Color("empty")
  val white = Color("white")
  val blue = Color("blue")
  val purple = Color("purple")
  val green = Color("green")
  val pink = Color("pink")
  val red = Color("red")

  val allColors = Set(anyColor, emptyColor, white, blue, purple, green, pink, red)
}
