package models.compiler

import actors.messages.level.PathAndContinent
import compiler.Element
import compiler.processor._
import play.api.libs.json._

/*
 * Conveys the result of a mathbot processor execution to the web client.
 * Typically part of an array sent via web socket.
 */
case class ClientFrameLegacy(robotState: ClientRobotStateLegacy,
                             programState: String,
                             pathAndContinent: Option[PathAndContinent],
                             trace: Seq[ClientTrace],
                             index: Int) {
  def isSuccess: Boolean = programState == "success"
  def isFailure: Boolean = programState == "failure"
}

object ClientFrameLegacy {

  implicit val element: OWrites[Element] = new OWrites[Element] {
    override def writes(o: Element): JsObject =
      JsObject(
        Seq("name" -> JsString(o.color.toString).asInstanceOf[JsValue],
            "original" -> JsBoolean(o.original).asInstanceOf[JsValue])
      )
  }

  implicit val clientFrameWrites: OWrites[ClientFrameLegacy] = Json.writes[ClientFrameLegacy]

  def apply(frame: Frame, pathAndContinent: Option[PathAndContinent] = None): ClientFrameLegacy =
    ClientFrameLegacy(frame, "running", pathAndContinent)

  // stepData is the step data to render at this point
  def success(frame: Frame, pathAndContinent: PathAndContinent): ClientFrameLegacy =
    ClientFrameLegacy(frame, "success", Some(pathAndContinent))

  // stepData is the step data to render at this point
  def failure(frame: Frame, pathAndContinent: PathAndContinent): ClientFrameLegacy =
    ClientFrameLegacy(frame, "failure", Some(pathAndContinent))

  def apply(frame: Frame, programState: String, pathAndContinent: Option[PathAndContinent]): ClientFrameLegacy =
    ClientFrameLegacy(ClientRobotStateLegacy(frame),
                programState,
                pathAndContinent,
                Seq(ClientTrace(frame.traceTag)),
                index = frame.index)
}
