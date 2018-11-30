package models

import actors.messages.level.HintTaken
import play.api.libs.json.Json

object VideoHint {
  final val tokenIdField: String = "tokenId"

  implicit val videoHintFormat = Json.format[VideoHint]
}
/*
 * VideoHint
 * Keeps a list of all the videos a user has watched to be iterated over once per hour in order
 * to give the user their stars back.
 * */
case class VideoHint(tokenId: String, videosWatched: List[HintTaken])
