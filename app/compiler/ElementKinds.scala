package compiler

object ElementKinds extends Enumeration {
  protected case class ElementKind(name: String, value: Double = 0) extends super.Val {
    override def toString(): String = name
    def toElement: Element =
      Element(original = true, name = this.name, image = this.name, color = this, value = this.value)
  }

  def from(name: String): Option[Value] = ElementKinds.all.find(c => c.name == name)
  /*
   * @deprecated
   * PreparedFunctions converts these to the appropriate denomination
   * */
  val blue = ElementKind("blue")
  val purple = ElementKind("purple")
  val green = ElementKind("green")
  val pink = ElementKind("pink")
  val red = ElementKind("red")

  // if this color ("white") changes name please update `convertColors` function in `actors/messages/PreparedFunctions.scala` to the new default color
  val white = ElementKind("white") // default -> any carrying state
  val anyColor = ElementKind("any") // if robot is carrying anything
  val emptyColor = ElementKind("empty") // if robot is carrying nothing

  val one = ElementKind("1", 1)
  val ten = ElementKind("10", 10)
  val hundred = ElementKind("100", 100)
  val thousand = ElementKind("1000", 1000)
  val tenThousand = ElementKind("10000", 10000)
  val negPt00001 = ElementKind("-.00001", -.00001)
  val negPt0001 = ElementKind("-.0001", -.0001)
  val negPt001 = ElementKind("-.001", -.001)
  val negPt01 = ElementKind("-.01", -.01)
  val negPt1 = ElementKind("-.1", -.1)
  val negOne = ElementKind("-1", -1)
  val negTen = ElementKind("-10", -10)
  val negHundred = ElementKind("-100", -100)
  val negThousand = ElementKind("-1000", -1000)
  val negTenThousand = ElementKind("-10000", -10000)
  val x = ElementKind("x")
  val y = ElementKind("y")
  val z = ElementKind("z")
  val pt00001 = ElementKind(".00001", .00001)
  val pt0001 = ElementKind(".0001", .0001)
  val pt001 = ElementKind(".001", .001)
  val pt01 = ElementKind(".01", .01)
  val pt1 = ElementKind(".1", .1)
  val zero = ElementKind("0")

  val allColors: Set[ElementKind] = Set(anyColor, emptyColor, white, blue, purple, green, pink, red)
  val allNumbers: Set[ElementKind] = Set(
    negPt00001,
    negPt0001,
    negPt001,
    negPt01,
    negPt1,
    negOne,
    negTen,
    negHundred,
    negThousand,
    negTenThousand,
    pt00001,
    pt0001,
    pt001,
    pt01,
    pt1,
    zero,
    one,
    ten,
    hundred,
    thousand,
    tenThousand
  )
  val allLetters: Set[ElementKind] = Set(x, y, z)
  val all: Set[ElementKind] = allColors ++ allNumbers ++ allLetters

  val listedControlElements: List[Element] = List(
    white,
    anyColor,
    emptyColor
  ).map(ek => Element(original = true, name = ek.name, image = ek.name, color = ek, value = ek.value))
  val listedElements: List[Element] = List(
    white,
    anyColor,
    emptyColor,
    one,
    ten,
    hundred,
    thousand,
    tenThousand,
    negPt00001,
    negPt0001,
    negPt001,
    negPt01,
    negPt1,
    negOne,
    negTen,
    negHundred,
    negThousand,
    negTenThousand,
    x,
    y,
    z,
    pt00001,
    pt0001,
    pt001,
    pt01,
    pt1
  ).map(ek => Element(original = true, name = ek.name, image = ek.name, color = ek, value = ek.value))
}
