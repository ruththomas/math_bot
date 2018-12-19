package models.compiler

import actors.messages.level.PathAndContinent
import compiler.processor.Frame
import play.api.libs.json.{Json, OWrites}

/*
 * Conveys the result of a mathbot processor execution to the web client.
 * Typically part of an array sent via web socket.
 */
case class ClientFrame(robotState: ClientRobotState,
                       programState: String,
                       pathAndContinent: Option[PathAndContinent],
                       trace: Seq[ClientTrace],
                       index: Option[Int]) {
  def isSuccess: Boolean = programState == "success"
  def isFailure: Boolean = programState == "failure"
}

object ClientFrame {

  implicit val clientFrameWrites: OWrites[ClientFrame] = Json.writes[ClientFrame]

  def apply(frame: Frame, pathAndContinent: Option[PathAndContinent] = None): ClientFrame =
    ClientFrame(frame, "running", pathAndContinent)

  // stepData is the step data to render at this point
  def success(frame: Frame, pathAndContinent: PathAndContinent): ClientFrame =
    ClientFrame(frame, "success", Some(pathAndContinent))

  // stepData is the step data to render at this point
  def failure(frame: Frame, pathAndContinent: PathAndContinent): ClientFrame =
    ClientFrame(frame, "failure", Some(pathAndContinent))

  def apply(frame: Frame, programState: String, pathAndContinent: Option[PathAndContinent]): ClientFrame =
    ClientFrame(ClientRobotState(frame),
                programState,
                pathAndContinent,
                Seq(ClientTrace(frame.traceTag)),
                index = frame.index)
}
