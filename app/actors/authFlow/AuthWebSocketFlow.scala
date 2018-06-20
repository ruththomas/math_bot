package actors.authFlow

import akka.NotUsed
import akka.actor.ActorRef
import akka.http.scaladsl.model.ws.Message
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{ Flow, Source }
import dataentry.utility.SecureIdentifier

case class RequestSession()
case class ResumeSession(sessionId : SecureIdentifier)
case class ProvideSession(sessionId : SecureIdentifier)
case class NeedsAuthorization(sessionId : SecureIdentifier)
case class IsAuthorized(sessionId : SecureIdentifier)
case class InvalidMessage(msg : String)

object AuthWebsocketFlow {
  
  def convertFlow : Flow[Any, Message, Any]

  def apply(provideActor : ActorRef => Unit) : Flow[Message, Message, Any] = {

    val actor = Source.actorRef[Message](16, OverflowStrategy.dropNew).mapMaterializedValue(ar => provideActor(ar))

    Flow[Message].map {
      case _ : RequestSession =>
      case ResumeSession(msg) =>
      case m  =>
        InvalidMessage(m.toString)
    }.merge(actor)
      .via[Any, Message](convertFlow)
  }

}
