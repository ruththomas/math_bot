package models.compiler

import actors.messages.level.PathAndContinent
import compiler.Element
import compiler.processor._
import play.api.libs.json._

/*
 * Conveys the result of a mathbot processor execution to the web client.
 * Typically part of an array sent via web socket.
 */
case class ClientFrame(robotState: ClientRobotState,
                       programState: String,
                       pathAndContinent: Option[PathAndContinent],
                       trace: Seq[ClientTrace],
                       index: Int,
                       change : Option[FrameChange]) {
  def isSuccess: Boolean = programState == "success"
  def isFailure: Boolean = programState == "failure"
}

object ClientFrame {

  // The "change" implicits are temporary for debugging. Eventually the frame change field will be removed.
  implicit val element: OWrites[Element] = new OWrites[Element] {
    override def writes(o : Element) : JsObject = JsObject(Seq("name" -> JsString(o.color.toString).asInstanceOf[JsValue], "original" -> JsBoolean(o.original).asInstanceOf[JsValue]))
}
  implicit val robotLocationChange: OWrites[RobotLocationChange] = Json.writes[RobotLocationChange]
  implicit val robotOrientationChange: OWrites[RobotOrientationChange] = Json.writes[RobotOrientationChange]
  implicit val elementChange: OWrites[ElementChange] = Json.writes[ElementChange]
  implicit val gridCellChange: OWrites[GridCellChange] = Json.writes[GridCellChange]
  implicit val frameChangeWrite : OWrites[FrameChange] = Json.writes[FrameChange]

  implicit val clientFrameWrites: OWrites[ClientFrame] = Json.writes[ClientFrame]
}
