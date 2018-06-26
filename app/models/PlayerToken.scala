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

  /*
  implicit val tokenReads: Reads[PlayerToken] = (
    (JsPath \ PlayerToken.tokenIdField).read[String] and
    (JsPath \ PlayerToken.lambdas).readNullable[Lambdas] and
    (JsPath \ PlayerToken.statsField).readNullable[Stats] and
    (JsPath \ PlayerToken.randomImagesField).readNullable[List[String]]
  )(PlayerToken.apply _)

  implicit val tokenWrites: Writes[PlayerToken] = (
    (JsPath \ PlayerToken.tokenIdField).write[String] and
    (JsPath \ PlayerToken.lambdas).writeNullable[Lambdas] and
    (JsPath \ PlayerToken.statsField).writeNullable[Stats] and
    (JsPath \ PlayerToken.randomImagesField).writeNullable[List[String]]
  )(unlift(PlayerToken.unapply))
 */
}
