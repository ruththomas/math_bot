package actors.authFlow

import akka.actor.ActorRef
import akka.http.scaladsl.model.ws.{ Message, TextMessage }
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{ Flow, Source }
import utils.SecureIdentifier
import model.{ PlayerTokenDAO, SessionDAO }
import play.api.libs.json._

import scala.collection.immutable

trait SocketAction {
  val action: Symbol
}

case class RequestSession(action: Symbol = 'requestSession) extends SocketAction
case class ResumeSession(sessionId: SecureIdentifier, action: Symbol = 'resumeSession) extends SocketAction
case class ProvideSession(sessionId: SecureIdentifier, action: Symbol = 'provideSession) extends SocketAction
case class NeedsAuthorization(sessionId: SecureIdentifier, action: Symbol = 'needsAuthorization) extends SocketAction
case class IsAuthorized(sessionId: SecureIdentifier, action: Symbol = 'isAuthorized) extends SocketAction
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
  implicit val isAuthorizedFormat : Format[IsAuthorized] = Json.format[IsAuthorized]
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
      case msg: IsAuthorized => Json.toJson[IsAuthorized](msg)
      case msg: InvalidMessage => Json.toJson[InvalidMessage](msg)
      case msg: SocketAction => Json.toJson[InvalidMessage](InvalidMessage(msg.toString))
    })
  )

  def apply(provideActor: ActorRef => Unit, sessionDAO: SessionDAO): Flow[Message, Message, Any] = {

    val actor = Source.actorRef[SocketAction](16, OverflowStrategy.dropNew).mapMaterializedValue(ar => provideActor(ar))

    Flow[Message]
      .map[SocketAction](fromMessage)
      .mapConcat[SocketAction] {
        case _: RequestSession =>
          val sid = SecureIdentifier(32)
          sessionDAO.insert(sid)
          immutable.Seq(ProvideSession(sid), NeedsAuthorization(sid))
        case ResumeSession(msg, _) =>
          immutable.Seq(InvalidMessage("standin"))
        case m =>
          immutable.Seq(InvalidMessage(m.toString))
      }
      .merge(actor)
      .map[Message](toMessage)
  }

}
