package compiler

import compiler.processor.{ Frame, Processor }
import configuration.CompilerConfiguration

object FrameController {
  def apply(program : GridAndProgram, successCheck : Grid => Boolean, executeEachFrame : Boolean, config : CompilerConfiguration) : FrameController =
    new FrameController(program, successCheck, executeEachFrame, config)
}

class FrameController(program: GridAndProgram, successCheck : Grid => Boolean, executeEachFrame : Boolean, config: CompilerConfiguration) {

  private val processor = Processor(program, config)

  private val frames : Stream[Frame] = processor.execute(successCheck, executeEachFrame)

  def request(index : Int, count : Int, direction : Int) : Seq[Frame] = {
    val delta = Math.abs(count) * direction

    val from = Math.min(index, index + delta)
    val until = Math.max(index, index + delta)

    val requestedFrames = frames.slice(from, until)

    direction match {
      case 0 => requestedFrames // Zero defaults to all frames
      case 1 => requestedFrames
      case -1 => requestedFrames.reverse
      case f if f > 1 => requestedFrames.zipWithIndex.filter(_._2 % f == 0).map(_._1)
      case f if f < -1 => requestedFrames.reverse.zipWithIndex.filter(_._2 % f == 0).map(_._1)
    }

  }


}
