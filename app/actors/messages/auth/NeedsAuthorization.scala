package actors.messages.auth

import actors.messages.MessageAction
import utils.SecureIdentifier

case class NeedsAuthorization(sessionId : SecureIdentifier, authUrls : Seq[AuthUrl], action : Symbol = 'needsAuthorization) extends MessageAction
