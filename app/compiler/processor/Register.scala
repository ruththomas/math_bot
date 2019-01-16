package compiler.processor

import compiler.{ Cell, CellType, Element }

object AnimationType extends Enumeration {
  val Bumped : AnimationType.Value = Value("bumped")
}

case class Register(holdingCell: Cell = Cell(cellType = CellType.RobotHolding),
                    animation: Option[AnimationType.Value] = None) {
  def clearAnimation(): Register = if (animation.isDefined) this.copy(animation = None) else this

  def pop(): Option[(Register, Element)] = holdingCell.pop() match {
    case (Some(newCell), Some(topElement)) => Some((this.copy(holdingCell = newCell), topElement))
    case _ => None
  }

  def popDelta(): Option[(Register, ElementChange, Element)] = holdingCell.pop() match {
    case (Some(newCell), Some(topElement)) =>
      Some((this.copy(holdingCell = newCell), ElementChange(pop = Some(topElement)), topElement))
    case _ => None
  }


  def push(newElement : Option[Element]): Register =
    newElement match {
      case Some(element) => this.copy(holdingCell = this.holdingCell.push(element))
      case _ =>  this // No change
    }

  def peek() : Option[Element] = holdingCell.contents.headOption

}
