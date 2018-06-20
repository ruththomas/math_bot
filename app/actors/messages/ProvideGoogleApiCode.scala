package dataentry.actors.messages

import dataentry.utility.SecureIdentifier

case class ProvideGoogleApiCode(sessionId : SecureIdentifier, code : String)
