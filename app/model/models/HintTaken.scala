package model.models

import play.api.libs.json.Json
import types.{LevelName, StepName}

object HintTaken {
  implicit val hintTakenFormat = Json.format[HintTaken]
}
/*
 * HintsTaken
 * Tracks the time of the last video watched per level step
 * videosWatched property may be unimportant here since the stars are tracked in the
 * playerToken
 * */
case class HintTaken(level: LevelName, step: StepName, timeStamp: Long, hintCount: Int, stars: Int)
