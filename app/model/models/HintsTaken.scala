package model.models

import play.api.libs.json.Json
import types.TokenId

object HintsTaken {
  final val tokenIdField: String = "tokenId"

  implicit val videoHintFormat = Json.format[HintsTaken]
}
/*
 * VideoHint
 * Keeps a list of all the videos a user has watched to be iterated over once per hour in order
 * to give the user their stars back.
 * */
case class HintsTaken(tokenId: TokenId, videosWatched: List[HintTaken])
