package dataentry.actors.messages

import dataentry.utility.SecureIdentifier

case class RevokeTokens(sessionId : SecureIdentifier, access_token : String)
