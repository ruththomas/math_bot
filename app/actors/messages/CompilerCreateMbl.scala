package actors.messages
import actors.messages.level.Problem

case class CompilerCreateMbl(steps : Int, problem : Problem, mbl : String)
