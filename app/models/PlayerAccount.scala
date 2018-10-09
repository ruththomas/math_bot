package models

import play.api.libs.json.{Json, OFormat}
import types.TokenId

object PlayerAccount {
  implicit val playerAccountFormat: OFormat[PlayerAccount] = Json.format[PlayerAccount]
}

case class PlayerAccount(tokenId: TokenId,
                         iss: String,
                         sub: String,
                         email: String,
                         name: String,
                         picture: Option[String] = None,
                         isAdmin: Boolean = false)
