package actors.messages

import actors.messages.level.Problem

case class ProcessorCreateMbl(
                               frames : ProcessorFrameSelector, // Selection of frames requested from the processor
                               problem: Problem, // The problem used to determine success or failure of the current program.
                               mbl: String // The mathbot language program to execute
                          )
