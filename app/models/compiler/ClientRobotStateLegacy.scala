package models.compiler

import compiler.Point
import compiler.processor.{AnimationType, Frame}
import play.api.libs.json.{JsPath, Writes}

case class ClientRobotStateLegacy(location: Point,
                                  orientation: String,
                                  holding: List[String],
                                  animation: Option[AnimationType.Value],
                                  grid: Option[ClientGrid])

object ClientRobotStateLegacy {

  import play.api.libs.functional.syntax._

  implicit val robotStateWrite: Writes[ClientRobotStateLegacy] = (
    (JsPath \ "location").write[Point] and
    (JsPath \ "orientation").write[String] and
    (JsPath \ "holding").write[List[String]] and
    (JsPath \ "animation").write[Option[AnimationType.Value]] and
    (JsPath \ "grid").write[Option[ClientGrid]]
  )(unlift(ClientRobotStateLegacy.unapply))

  def apply(frame: Frame): ClientRobotStateLegacy = new ClientRobotStateLegacy(
    location = frame.board.robotLocation,
    orientation = frame.board.robotOrientation,
    holding = frame.register.holdingCell.contents.map(v => v.image),
    animation = frame.register.animation,
    grid = Some(ClientGrid(frame.board))
  )

  def apply(location: Point, orientation: String, holding: List[String]): ClientRobotStateLegacy =
    new ClientRobotStateLegacy(location, orientation, holding, None, None)

  def toTuple(state: ClientRobotStateLegacy) =
    Some((state.location.x, state.location.y, state.orientation, state.animation.map(a => a.toString)))

}
