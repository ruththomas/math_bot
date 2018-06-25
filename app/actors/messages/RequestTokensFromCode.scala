package dataentry.actors.messages

import utils.SecureIdentifier

case class RequestTokensFromCode(sessionId : SecureIdentifier, code : String)
