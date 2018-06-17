package model.models

import types.{LevelName, StepName}

/*
 * HintsTaken
 * Tracks the time of the last video watched per level step
 * videosWatched property may be unimportant here since the stars are tracked in the
 * playerToken
 * */
case class HintsTaken(level: LevelName, step: StepName, timeStamp: Long, videosWatched: Int)
