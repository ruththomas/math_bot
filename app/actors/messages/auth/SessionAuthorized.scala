package actors.messages.auth

import actors.messages.MessageAction
import utils.SecureIdentifier

case class SessionAuthorized(sessionId: SecureIdentifier,
                             name : String,
                             picture : String,
                             sub: String,
                             email: String,
                             action: Symbol = 'sessionAuthorized
                            )
    extends MessageAction
