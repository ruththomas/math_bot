package actors.messages.auth

import akka.http.scaladsl.model.Uri
import utils.SecureIdentifier

case class TokensFromCodeFailure(sessionId : SecureIdentifier, uri : Uri, reason : String)
