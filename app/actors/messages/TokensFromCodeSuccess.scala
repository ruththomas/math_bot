package dataentry.actors.messages

import dataentry.utility.SecureIdentifier
import dataentry.actors.models.GoogleApiHelpers.GoogleTokens

case class TokensFromCodeSuccess(sessionId : SecureIdentifier, tokens : GoogleTokens)
