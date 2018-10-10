package models
import types.TokenId
import utils.SecureIdentifier

case class AdminAuth(tokenId: TokenId, adminAuthId: SecureIdentifier, createdAt: Long)
