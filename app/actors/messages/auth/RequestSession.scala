package actors.messages.auth

import actors.messages.MessageAction

case class RequestSession(action: Symbol = 'requestSession) extends MessageAction
