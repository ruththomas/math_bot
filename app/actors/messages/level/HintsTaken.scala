package actors.messages.level

import play.api.libs.json.Json

object HintsTaken {
  final val tokenIdField: String = "tokenId"
  final val listLabel: String = "list"

  implicit val videoHintFormat = Json.format[HintsTaken]
}
/*
 * VideoHintControls
 * Keeps a list of all the videos a user has watched to be iterated over once per hour in order
 * to give the user their stars back.
 * */
case class HintsTaken(tokenId: String, list: Map[String, HintTaken])
