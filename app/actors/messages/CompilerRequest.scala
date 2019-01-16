package actors.messages

import play.api.libs.json.{JsValue, Json, Reads}

case class FrameRequest(
                       index : Int, // The frame index to start the frame sequence with
                       count: Int, // The number of frames to request
                       direction: Int // Direction and skip relative to the index. A positive or negative non-zero number.  Values less than -1 or larger than 1 will skip frames
                       )

case class CompilerRequest(
    steps: Option[Int], // Maximum number of client frames for the compiler to respond with, cannot appear with frame request
    problem: Option[String], // The encrypted problem the user is solving
    halt: Option[Boolean], // Stops or destroys the processor
    create: Option[Boolean], // When true, creates a new compiler to run the program
    mbl : Option[String], // Program in the math_bot language
    frames: Option[FrameRequest] // A frame request for forward/reverse, skipping, and random access to frames. Cannot appear with steps
)

object CompilerRequest {
  implicit val frameRequestReads: Reads[FrameRequest] = Json.reads[FrameRequest]
  implicit val compilerRequestReads: Reads[CompilerRequest] = Json.reads[CompilerRequest]
}