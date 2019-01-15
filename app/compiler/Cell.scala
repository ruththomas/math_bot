package compiler

case class Cell(cellType: CellType.Value, contents: List[Element] = List.empty[Element]) {

  def pop(): (Option[Cell], Option[Element]) = contents.headOption match {
    case Some(topElement) =>
      if (topElement.original) {
        (Some(this), Some(topElement.copy(original = false)))
      } else {
        contents.tail match {
          case Nil if this.cellType == CellType.EmptySpace => (None, Some(topElement))
          case Nil => (Some(this.copy(contents = List.empty[Element])), Some(topElement))
          case remaining => (Some(this.copy(contents = remaining)), Some(topElement))
        }
      }
    case None => (Some(this), None)
  }

  /***
   * Provides the top element of a cell.  The element is removed if its not an original element.
   * @return (_1 = The new cell or None if the cell is empty,
   *         _2 = The element that was removed or None if the cell doesn't change
   *         _3 =
   */
  def popDelta(): (Option[Cell], Option[Element], Option[Element]) =
    contents.headOption match {
      case Some(topElement) if topElement.original =>
        (Some(this), None, Some(topElement.copy(original = false)))
      case Some(topElement) =>
        contents.tail match {
          case Nil if this.cellType == CellType.EmptySpace =>
            (None, Some(topElement), Some(topElement))
          case Nil => // Probably the holding cell
            (Some(this.copy(contents = List.empty[Element])), Some(topElement), Some(topElement))
          case remaining =>
            (Some(this.copy(contents = remaining)), Some(topElement), Some(topElement))
        }
      case None =>
        (Some(this), None, None)
    }

  def push(newElement: Element): Cell = this.copy(contents = newElement :: this.contents)
}

object Cell {

  // Adds an element to the "top" of a cell and returns the updated cell. Will create the cell if needed
  def setItemDown(cell: Option[Cell], newElement: Element): Cell = cell match {
    case Some(existingCell) => existingCell.push(newElement)
    case None => Cell(CellType.EmptySpace, List(newElement))
  }

  /*@deprecated("This method will be removed after scrub-through work is completed", "Issue #438")*/
  def pickupItemOld(cell: Option[Cell]): Option[(Option[Cell], Option[Element])] = cell match {
    case Some(existingCell) =>
      Some(existingCell.pop())
    case None => None
  }

  // Removes the top element from the cell.  Returns the new cell if it changed, the element if it was removed (non-original), the element removed
  def pickupItem(cell: Option[Cell]): (Option[Cell], Option[Element], Option[Element]) =
    cell match {
      case Some(existingCell) =>
        existingCell.popDelta()
      case None =>
        (None, None, None)
    }
}

case class CellChange(location: Point, contents: List[Element], cellCleared: Boolean)

object CellChange {
  def apply(robotLocation: Point, changedCell: Option[Cell]): CellChange =
    CellChange(
      location = robotLocation,
      contents = changedCell.map(_.contents).getOrElse(List.empty[Element]),
      cellCleared = changedCell.isEmpty
    )
}
