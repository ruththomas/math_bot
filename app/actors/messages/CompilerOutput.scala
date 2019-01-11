package actors.messages

import models.compiler.ClientFrame

case class CompilerOutput(frames: Seq[ClientFrame])
