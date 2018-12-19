package compiler

case class Element(original: Boolean, name: String, image: String, color: ElementKinds.Value, value: Double) {
  val valueToBigDec: BigDecimal = BigDecimal(this.value)
}
