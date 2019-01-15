package actors.messages

case class ProcessorContinue(
                              frames: ProcessorFrameSelector // Selection of frames request from the processor
)
