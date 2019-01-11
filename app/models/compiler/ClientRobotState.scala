package models.compiler

import compiler.Point
import compiler.processor.{AnimationType, Frame}
import play.api.libs.json._

case class ClientRobotState(location: Option[Point],
                            orientation: Option[String],
                            holding: Option[List[String]],
                            animation: Option[AnimationType.Value],
                            grid: Option[ClientGrid])

object ClientRobotState {

  implicit val clientRobotStateWrite: Writes[ClientRobotState] = new Writes[ClientRobotState] {
    override def writes(o: ClientRobotState): JsValue =
      JsObject(
        Seq(
          o.location.map("location" -> Json.toJsObject(_)),
          o.orientation.map("orientation" -> JsString(_)),
          o.holding.map("holding" -> Json.toJson(_)),
          o.animation.map("holding" -> Json.toJson(_)),
          o.grid.map("holding" -> Json.toJson(_))
        ).flatten
      )
  }

  def apply(frame: Frame): ClientRobotState = new ClientRobotState(
    location = Some(frame.robotLocation.map(l => Point(l.x, l.y)).getOrElse(Point(0, 0))),
    orientation = Some(frame.robotLocation.map(l => l.orientation).getOrElse("0")),
    animation = frame.register.animation,
    grid = Some(ClientGrid(frame.board)),
    holding = Some(frame.register.holdingCell.contents.map(v => v.image))
  )

  def apply(location: Point, orientation: String, holding: List[String]): ClientRobotState =
    new ClientRobotState(Some(location), Some(orientation), Some(holding), None, None)

}
