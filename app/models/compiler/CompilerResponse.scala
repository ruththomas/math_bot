package models.compiler

import actors.messages.level.Problem

/* The response sent back to a client that requests execution of a program. */
case class CompilerResponse(frames: List[ClientFrame] = List.empty[ClientFrame],
                            problem: Option[Problem] = None,
                            halted: Option[Boolean] = None,
                            error: Option[String] = None)
