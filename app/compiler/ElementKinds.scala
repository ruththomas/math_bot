package compiler

object ElementKinds extends Enumeration {
  protected case class ElementKind(name : String) extends super.Val {
    override def toString(): String = name
  }

  def from(name : String): Option[Value] = ElementKinds.all.find(c => c.name == name)

  val anyColor = ElementKind("any")
  val emptyColor = ElementKind("empty")
  // if this color (white) changes name please update `convertColors` function in `actors/messages/PreparedFunctions.scala` to the new default color
  val white = ElementKind("white")
  val blue = ElementKind("blue")
  val purple = ElementKind("purple")
  val green = ElementKind("green")
  val pink = ElementKind("pink")
  val red = ElementKind("red")

  val negPt00001 = ElementKind("-.00001")
  val negPt0001 = ElementKind("-.0001")
  val negPt001 = ElementKind("-.001")
  val negPt01 = ElementKind("-.01")
  val negPt1 = ElementKind("-.1")
  val negOne = ElementKind("-1")
  val negTen = ElementKind("-10")
  val negHundred = ElementKind("-100")
  val negThousand = ElementKind("-1000")
  val negTenThousand = ElementKind("-10000")
  val x = ElementKind("x")
  val y = ElementKind("y")
  val z = ElementKind("z")
  val pt00001 = ElementKind(".00001")
  val pt0001 = ElementKind(".0001")
  val pt001 = ElementKind(".001")
  val pt01 = ElementKind(".01")
  val pt1 = ElementKind(".1")
  val zero = ElementKind("0")
  val one = ElementKind("1")
  val ten = ElementKind("10")
  val hundred = ElementKind("100")
  val thousand = ElementKind("1000")
  val tenThousand = ElementKind("10000")

  val allColors: Set[ElementKind] = Set(anyColor, emptyColor, white, blue, purple, green, pink, red)
  val allNumbers : Set[ElementKind] = Set(negPt00001, negPt0001, negPt001, negPt01, negPt1, negOne, negTen, negHundred, negThousand, negTenThousand, pt00001, pt0001, pt001, pt01, pt1, zero, one, ten, hundred, thousand, tenThousand)
  val allLetters : Set[ElementKind] = Set(x, y, z)
  val all : Set[ElementKind] = allColors ++ allNumbers ++ allLetters

  // Guarantees order
  val listedColors: List[ElementKind] = List(anyColor, emptyColor, white, blue, purple, green, pink, red)
}
