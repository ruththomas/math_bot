package actors.messages

import models.compiler.ClientFrameLegacy

case class CompilerOutputLegacy(frames: Seq[ClientFrameLegacy])
