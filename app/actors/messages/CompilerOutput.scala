package actors.messages

import controllers.MathBotCompiler.ClientFrame
import models.Problem

case class CompilerOutput(frames : List[ClientFrame], problem: Problem)
