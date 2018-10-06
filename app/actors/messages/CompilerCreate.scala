package actors.messages

import models.Problem

case class CompilerCreate(steps : Int, problem : Problem)
