package dataentry.actors.messages

import akka.http.scaladsl.model.{ StatusCode, Uri }
import utils.SecureIdentifier

case class TokensFromCodeFailure(sessionId : SecureIdentifier, uri : Uri, statusCode : StatusCode, body : String)
