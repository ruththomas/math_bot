package actors.messages

case class ProcessorFrameSelector(
                        index: Int, // The frame index to start the frame selection with
                        count: Int, // The number of frames in the selection
                        direction: Int // The direction and skip relative to the index.  Direction and skip relative to the index. A positive or negative non-zero number.
                        // The magnitude of the value determines the frame skiping. e.g. a value of -2 returns every other frame starting with the frame at index.
                        )
