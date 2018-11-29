package actors.messages

import actors.messages.level.Problem
import models.compiler.ClientFrame
// TODO: the problem field is not used by the client (its all in the last frame now) so it should be refactored out
case class CompilerOutput(frames: List[ClientFrame], problem: Problem)
