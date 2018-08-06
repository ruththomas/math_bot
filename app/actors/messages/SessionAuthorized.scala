package actors.messages

import actors.authFlow.SocketAction
import utils.SecureIdentifier

case class SessionAuthorized(sessionId: SecureIdentifier,
                             name : String,
                             given_name: String,
                             family_name : String,
                             picture : String,
                             sub: String,
                             email: String,
                             action: Symbol = 'sessionAuthorized
                            )
    extends SocketAction
