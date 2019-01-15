package compiler.processor

import compiler.{ Element, Point }

case class FrameChange(
                       location: Option[RobotLocationChange] = None,
                       orientation: Option[RobotOrientationChange] = None,
                       grid: Seq[GridCellChange] = Seq.empty[GridCellChange],
                       holding: Seq[ElementChange] = Seq.empty[ElementChange]
                      ) {
  def inverse : FrameChange = FrameChange(
    location.map(_.inverse),
    orientation.map(_.inverse),
    grid.map(_.inverse),
    holding.map(_.inverse)
  )

}

object FrameChange {
  def apply(location : RobotLocationChange) : FrameChange = FrameChange(location = Some(location))
  def apply(from: Point, to: Point) : FrameChange = FrameChange(RobotLocationChange(from, to, bump = false))
  def apply(from: Point, to: Point, bump : Boolean) : FrameChange = FrameChange(RobotLocationChange(from, to, bump))
  def apply(orientation : RobotOrientationChange) : FrameChange = FrameChange(orientation = Some(orientation))
  def apply(from: String, to: String) : FrameChange = FrameChange(RobotOrientationChange(from, to))
  def apply(grid : GridCellChange, holding: ElementChange) : FrameChange = FrameChange(grid = Seq(grid), holding = Seq(holding))
  def apply(location: Point, grid : ElementChange, holding: ElementChange) : FrameChange = FrameChange(grid = Seq(GridCellChange(location, grid)), holding = Seq(holding))
}

case class RobotLocationChange(from: Point, to: Point, bump : Boolean){
  def inverse: RobotLocationChange = RobotLocationChange(to, from, bump)
}

case class RobotOrientationChange(from: String, to: String){
  def inverse: RobotOrientationChange = RobotOrientationChange(to, from)

}

case class ElementChange(push : Option[Element] = None, pop: Option[Element] = None) {
  def inverse : ElementChange = ElementChange(push = pop, pop = push)
}

case class GridCellChange(location : Point, change : ElementChange){
  def inverse: GridCellChange = GridCellChange(location, change.inverse)
}