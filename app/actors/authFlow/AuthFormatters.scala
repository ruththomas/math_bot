package actors.authFlow

import actors.messages.SessionAuthorized
import akka.actor.ActorRef
import akka.http.scaladsl.model.ws.{ Message, TextMessage }
import akka.http.scaladsl.util.FastFuture
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{ Flow, Source }
import daos.SessionDAO
import dataentry.caches.KeyValueCache
import models.JwtToken
import play.api.libs.json._
import utils.SecureIdentifier

import scala.collection.immutable
import scala.concurrent.{ ExecutionContext, Future }

trait SocketAction {
  val action: Symbol
}

case class RequestSession(action: Symbol = 'requestSession) extends SocketAction
case class ResumeSession(sessionId: SecureIdentifier, action: Symbol = 'resumeSession) extends SocketAction
case class AuthUrl(provider : String, url : String)
case class NeedsAuthorization(sessionId : SecureIdentifier, authUrls : Seq[AuthUrl], action : Symbol = 'needsAuthorization) extends SocketAction

object AuthFormatters {

  implicit val symbolFormatter = new Format[Symbol] {
    override def writes(o : Symbol) : JsValue = JsString(o.name)

    override def reads(json : JsValue) : JsResult[Symbol] = json match {
      case JsString(name) => JsSuccess(Symbol(name))
      case _ => JsError("Unable to read Symbol value.")
    }
  }

  implicit val authUrlFormat : Format[AuthUrl] = Json.format[AuthUrl]
  implicit val requestSessionFormat : Format[RequestSession] = Json.format[RequestSession]
  implicit val resumeSessionFormat : Format[ResumeSession] = Json.format[ResumeSession]
  implicit val needsAuthorizationFormat : Format[NeedsAuthorization] = Json.format[NeedsAuthorization]
  implicit val sessionAuthorizedFormat : Format[SessionAuthorized] = Json.format[SessionAuthorized]

}
