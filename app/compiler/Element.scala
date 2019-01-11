package compiler

/***
  * An element is a value that can be placed in a grid cell or held by math bot
  * @param original When true, the element cannot be removed from a cell and only can be copied
  * @param name The name of the element, deprecated because the name can be derived from the [[color]] field
  * @param image The visual representation of the element, deprecated because the image can be derived from the [[color]] field
  * @param color The color, or more generally, the kind of element
  * @param value The value of an element, deprecated because it can be derived from the color field
  */
case class Element(original: Boolean, /*@deprecated("Should use the color field", "Issue #627")*/ name: String, /*@deprecated("Should use the color field", "Issue #627")*/ image: String, color: ElementKinds.Value, /*@deprecated("Should use the color field", "Issue #627")*/ value: Double) {
  val valueToBigDec: BigDecimal = BigDecimal(this.value)
}
