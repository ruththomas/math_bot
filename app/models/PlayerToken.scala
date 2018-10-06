package models

import utils.SecureIdentifier
import actors.messages.PreparedStepData
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class PlayerToken(
    token_id: String,
    lambdas: Option[Lambdas] = None,
    stats: Option[Stats] = None,
    randomImages: Option[List[String]] = None,
    sessionId: Option[SecureIdentifier] = None
)

object PlayerToken {
  final val sessionId = "sessionId"
  final val tokenIdField = "token_id"
  final val lambdas = "lambdas"
  final val statsField = "stats"
  final val randomImagesField = "randomImages"

  implicit val jsonFormat = Json.format[PlayerToken]
}
