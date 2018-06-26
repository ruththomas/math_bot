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
case class ProvideSession(sessionId: SecureIdentifier, action: Symbol = 'provideSession) extends SocketAction
case class NeedsAuthorization(sessionId: SecureIdentifier, action: Symbol = 'needsAuthorization) extends SocketAction
case class InvalidMessage(msg: String, action: Symbol = 'invalidMessage) extends SocketAction

object AuthWebSocketFlow {

  implicit val symbolFormatter = new Format[Symbol] {
    override def writes(o : Symbol) : JsValue = JsString(o.name)

    override def reads(json : JsValue) : JsResult[Symbol] = json match {
      case JsString(name) => JsSuccess(Symbol(name))
      case _ => JsError("Unable to read Symbol value.")
    }
  }

  implicit val requestSessionFormat : Format[RequestSession] = Json.format[RequestSession]
  implicit val resumeSessionFormat : Format[ResumeSession] = Json.format[ResumeSession]
  implicit val provideSessionFormat : Format[ProvideSession] = Json.format[ProvideSession]
  implicit val needsAuthorizationFormat : Format[NeedsAuthorization] = Json.format[NeedsAuthorization]
  implicit val sessionAuthorizedFormat : Format[SessionAuthorized] = Json.format[SessionAuthorized]
  implicit val invalidMessageFormat : Format[InvalidMessage] = Json.format[InvalidMessage]

  def fromMessage(value: Message): SocketAction = {
    val json = Json.parse(value.asTextMessage.getStrictText)
    (json \ "action").asOpt[String].map(Symbol(_)) match {
      case Some('requestSession) => json.as[RequestSession]
      case Some('resumeSession) => json.as[ResumeSession]
    }
  }

  def toMessage(value: SocketAction): Message = TextMessage.Strict(
    Json.stringify(value match {
      case msg: ProvideSession => Json.toJson[ProvideSession](msg)
      case msg: NeedsAuthorization => Json.toJson[NeedsAuthorization](msg)
      case msg: SessionAuthorized => Json.toJson[SessionAuthorized](msg)
      case msg: InvalidMessage => Json.toJson[InvalidMessage](msg)
      case msg: SocketAction => Json.toJson[InvalidMessage](InvalidMessage(msg.toString))
    })
  )

  def apply(
             provideActor: ActorRef => Unit,
             sessionDAO: SessionDAO,
             sessionCache : KeyValueCache[SecureIdentifier, Option[JwtToken]]
           )(implicit ec : ExecutionContext): Flow[Message, Message, Any] = {

    val actor = Source.actorRef[SocketAction](16, OverflowStrategy.dropNew).mapMaterializedValue(ar => provideActor(ar))

    Flow[Message]
      .map[SocketAction](fromMessage)
      .mapConcat[Future[SocketAction]] {
        case _: RequestSession =>
            val sid = SecureIdentifier(32)
            sessionCache.put(sid, None)
            immutable.Seq(FastFuture.successful(ProvideSession(sid)), FastFuture.successful(NeedsAuthorization(sid)))
        case ResumeSession(sessionId, _) =>
          immutable.Seq(
          for {
            idTokenOpt <- sessionDAO.find(sessionId)
          } yield idTokenOpt match {
            case Some(idToken) => SessionAuthorized(sessionId, s"${idToken.getIssuerShortName}|${idToken.sub}", idToken.email)
            case _ => NeedsAuthorization(sessionId)
          }
          )
        case m =>
          immutable.Seq(FastFuture.successful(InvalidMessage(m.toString)))
      }
      .mapAsync[SocketAction](5)((f : Future[SocketAction]) => f) // Waits for the above futures to complete
      .merge(actor)
      .map[Message](toMessage)
  }

}
