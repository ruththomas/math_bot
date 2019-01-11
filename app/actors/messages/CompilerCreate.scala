package actors.messages
import actors.messages.level.Problem

case class CompilerCreate(steps : Int, problem : Problem)
