package models

import types.TokenId

case class PlayerAccount(tokenId: TokenId, iss: String, sub: String, email: String, name: String, picture: Option[String] = None, isAdmin: Boolean = false)
