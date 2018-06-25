package dataentry.actors.messages

import utils.SecureIdentifier

case class RevokeTokens(sessionId : SecureIdentifier, access_token : String)
