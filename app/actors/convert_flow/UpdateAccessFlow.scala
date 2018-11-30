package actors.convert_flow

import actors.messages.playeraccount.UpdateAccountAccess
import akka.NotUsed
import akka.actor.ActorRef
import akka.stream.scaladsl.Sink

object UpdateAccessFlow {

  def apply(tokenId: String, playerAccountActor: ActorRef): Sink[Any, NotUsed] = Sink.onComplete { _ =>
    playerAccountActor ! UpdateAccountAccess(tokenId)
  }
}
