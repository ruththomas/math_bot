package actors.messages.auth

import utils.SecureIdentifier

case class RequestTokensFromCode(sessionId : SecureIdentifier, code : String)
