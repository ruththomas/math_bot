package actors.messages.auth

import actors.GoogleApiHelpers.GoogleTokens
import utils.SecureIdentifier

case class TokensFromCodeSuccess(sessionId : SecureIdentifier, tokens : GoogleTokens)
