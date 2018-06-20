package dataentry.actors.messages

import dataentry.utility.SecureIdentifier

case class RequestTokensFromCode(sessionId : SecureIdentifier, code : String)
