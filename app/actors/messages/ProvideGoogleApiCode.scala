package actors.messages

import utils.SecureIdentifier

case class ProvideGoogleApiCode(sessionId : SecureIdentifier, code : String)
