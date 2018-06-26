package actors.messages

import actors.authFlow.SocketAction
import utils.SecureIdentifier

case class SessionAuthorized(sessionId : SecureIdentifier, sub: String, email : String, action : Symbol = 'sessionAuthorized) extends SocketAction
