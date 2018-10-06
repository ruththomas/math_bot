package actors.messages

import models.Problem

case class CompilerExecute(steps : Int, problem : Problem)

