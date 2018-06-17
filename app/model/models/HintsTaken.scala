package model.models
/*
 * HintsTaken
 * Tracks the time of the last video watched per level step
 * videosWatched property may be unimportant here since the stars are tracked in the
 * playerToken
 * */
case class HintsTaken(level: String, step: String, timeStamp: Long, videosWatched: Int)
