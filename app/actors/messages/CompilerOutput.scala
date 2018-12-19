package actors.messages

import actors.messages.level.Problem
import models.compiler.ClientFrame
case class CompilerOutput(frames: Seq[ClientFrame])
