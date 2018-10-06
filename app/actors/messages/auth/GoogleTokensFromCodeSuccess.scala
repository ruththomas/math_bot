package actors.messages.auth

import actors.GoogleApiHelpers.GoogleTokens
import utils.SecureIdentifier

case class GoogleTokensFromCodeSuccess(sessionId : SecureIdentifier, tokens : GoogleTokens)
