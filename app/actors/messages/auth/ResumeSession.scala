package actors.messages.auth

import actors.messages.MessageAction
import utils.SecureIdentifier

case class ResumeSession(sessionId: SecureIdentifier, action: Symbol = 'resumeSession) extends MessageAction
