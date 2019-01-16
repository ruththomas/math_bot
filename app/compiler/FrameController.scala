package compiler

import actors.messages.level.PathAndContinent
import compiler.operations.{ Final, Initial }
import compiler.processor._
import configuration.CompilerConfiguration
import models.compiler._

object FrameController {
  def apply(program: GridAndProgram,
            successCheck: Grid => Boolean,
            executeEachFrame: Boolean,
            config: CompilerConfiguration): FrameController =
    new FrameController(program, successCheck, executeEachFrame, config)
}

class FrameController(program: GridAndProgram,
                      successCheck: Grid => Boolean,
                      executeEachFrame: Boolean,
                      config: CompilerConfiguration) {

  private val processor = Processor(program, config)

  private val frames: Stream[Frame] = processor.execute(successCheck, executeEachFrame)

  private def toClientFrame(frame : Frame) : ClientFrame = {
    val location = frame.change.location.map(c => c.to)
    val orientation = frame.change.orientation.map(c => c.to)
    val holding = frame.change.holding match {
      case Nil =>
        None
      case _ =>
        Some(frame.register.holdingCell.contents.map(_.name))
    }
    val animation = frame.change.location match {
      case Some(RobotLocationChange(_, _, true)) => Some(AnimationType.Bumped)
      case _ => None
    }
    val grid = if (frame.change.grid.nonEmpty) {
      Some(ClientGrid(frame.change.grid.map(c => ClientCell(c.location, frame.board.getCell(c.location))).toSet))
    } else {
      Option.empty[ClientGrid]
    }
    val robotState =
      ClientRobotState(
        location,
        orientation,
        holding,
        animation,
        grid
      )
    val programState = (frame.operation, frame.success) match {
      case (Final, true) => "success"
      case (Final, false) => "failure"
      case _ => "running"
    }
    val pathAndContinent = Option.empty[PathAndContinent]
    val trace = Seq(ClientTrace(frame.traceTag))
    val index = frame.index
    ClientFrame(
      robotState,
      programState,
      pathAndContinent,
      trace,
      index,
      change = Some(frame.change)
    )
  }

  private def toClientFrameWithDefaults(frame: Frame) : ClientFrame = {
    val cf = toClientFrame(frame)
    cf.copy(
      robotState = ClientRobotState(
        cf.robotState.location.orElse(Some(frame.board.robotLocation)),
        cf.robotState.orientation.orElse(Some(frame.board.robotOrientation)),
        cf.robotState.holding.orElse(Some(frame.register.holdingCell.contents.map(_.name))),
        cf.robotState.animation,
        cf.robotState.grid
      )
    )
  }

  def request(index: Int, count: Int, direction: Int): Seq[ClientFrame] = {
    requestFrames(index, count, direction) match {
      case frame :: Nil =>
        Seq(toClientFrameWithDefaults(frame))
      case head +: tail =>
        toClientFrameWithDefaults(head) +: tail.map(toClientFrame)
      case _ =>
        Seq.empty[ClientFrame]
    }

  }

  def requestFrames(index: Int, count: Int, direction: Int): Seq[Frame] = {

    direction match {
      case 0 =>
        forwardSlice(frames, index, count) // Zero defaults to all frames but this is usually prevented by the request pipeline
      case 1 =>
        forwardSlice(frames, index, count)
      case -1 =>
        // A reversed frame is constructed by taking the change of the frame that follows it, and then inverting it.
        // e.g. The reverse of frame 1 is built by taking the change on frame 2 and then reversing the order of the change.
        reverseSlice(frames, index, count)
      case d if d > 1 =>
        val delta = count * d
        preserveGrouping(forwardSlice(frames, index, delta), d)

      case d if d < -1 =>
        val delta = count * -d
        preserveGrouping(reverseSlice(frames, index, delta), -d)
    }
  }

  private def forwardSlice(frames: Stream[Frame], index: Int, count: Int) =
    frames.slice(index, index + count)

  private def reverseSlice(frames: Stream[Frame], index: Int, count: Int) =
    // A reversed frame is constructed by taking the change of the frame that follows it, and then inverting it.
    // e.g. The reverse of frame 1 is built by taking the change on frame 2 and then reversing the order of the change.
    frames
      .slice(index + 1 - count, index + 2) // Because slice gets a frame "up to" the requested, we add one frame, the second value is the one needed for its "change" field
      .reverse
      .sliding(2) // Groups the frames into pairs
      .map(pair => pair.last.copy(change = pair.head.change.inverse)) // copy the change from the head frame into the last. This is the opposite of the explanation above because the frames are reversed
      .take(count)
      .toStream // Because there an extra frame on the end, we just take the first frames after materializing the stream

  private def preserveGrouping(frames: Stream[Frame], size: Int) = {
    // When merging frames, we have to keep the initial and final frames out of the merge
    frames match {
      case head +: middle :+ tail
          if (head.operation == Initial && tail.operation == Final) || (head.operation == Final && tail.operation == Initial) =>
        head +: middle.grouped(size).map(f => merge(f)).toSeq :+ tail
      case head +: rest if head.operation == Initial || head.operation == Final =>
        head +: rest.grouped(size).map(f => merge(f)).toSeq
      case init :+ tail if tail.operation == Final || tail.operation == Final =>
        init.grouped(size).map(f => merge(f)).toSeq :+ tail
      case all =>
        all.grouped(size).map(f => merge(f)).toSeq
    }
  }

  def merge(frames: Stream[Frame], invert: Boolean = false): Frame = {
    val changes = frames.map(_.change)
    val locationChanges = changes.flatMap(_.location) match {
      case head +: _ :+ last => Some(RobotLocationChange(head.from, last.to, last.bump))
      case l => l.headOption
    }
    val orientationChanges = changes.flatMap(_.orientation) match {
      case head +: _ :+ last => Some(RobotOrientationChange(head.from, last.to))
      case l => l.headOption
    }
    val holdingChanges = changes.flatMap(_.holding)
    val gridChanges = changes.flatMap(_.grid)

    frames.last.copy(change = FrameChange(
        location = locationChanges,
        orientation = orientationChanges,
        holding = holdingChanges,
        grid = gridChanges
      ))
  }

}
