package actors.messages

import models.Problem

case class CompilerCreateMbl(steps : Int, problem : Problem, mbl : String)
