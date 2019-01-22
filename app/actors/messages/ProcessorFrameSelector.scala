package actors.messages

case class ProcessorFrameSelector(
    previous: Int, // The previous frame displayed by the client, if there was no last frame just set it to the same as the index.
    index: Int, // The frame index to start the frame selection with
    count: Int, // The number of frames in the selection
    direction: Int // The direction and skip relative to the index.  Direction and skip relative to the index. A positive or negative non-zero number.
    // The magnitude of the value determines the frame skiping. e.g. a value of -2 returns every other frame starting with the frame at index.
)
