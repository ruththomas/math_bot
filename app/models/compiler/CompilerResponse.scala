package models.compiler

import actors.messages.level.Problem

/* The response sent back to a client that requests execution of a program. */
case class CompilerResponse(frames: Seq[ClientFrame] = Seq.empty[ClientFrame],
                            problem: Option[Problem] = None,
                            halted: Option[Boolean] = None,
                            error: Option[String] = None)
