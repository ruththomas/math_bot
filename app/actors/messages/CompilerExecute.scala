package actors.messages
import actors.messages.level.Problem

case class CompilerExecute(steps : Int, problem : Problem)
