package models

import play.api.libs.json._
import utils.SecureIdentifier

case class PasswordUpdate(email: String, recoveryId : SecureIdentifier, password : String)

object PasswordUpdate {

  implicit val passwordUpdateFormat: OFormat[PasswordUpdate] = Json.format[PasswordUpdate]

}
