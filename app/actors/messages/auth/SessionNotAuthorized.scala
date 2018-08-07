package actors.messages.auth

import actors.messages.MessageAction
import utils.SecureIdentifier

case class SessionNotAuthorized(sessionId : SecureIdentifier, reason : String, action : Symbol = 'sessionNotAuthorized) extends MessageAction
