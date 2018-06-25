package dataentry.actors.messages

import utils.SecureIdentifier
import dataentry.actors.models.GoogleApiHelpers.GoogleTokens

case class TokensFromCodeSuccess(sessionId : SecureIdentifier, tokens : GoogleTokens)
