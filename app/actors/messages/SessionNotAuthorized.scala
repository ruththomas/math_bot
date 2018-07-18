package actors.messages

import actors.authFlow.SocketAction
import utils.SecureIdentifier

case class SessionNotAuthorized(sessionId : SecureIdentifier, reason : String, action : Symbol = 'sessionNotAuthorized) extends SocketAction
