package actors

import actors.messages.ActorFailed
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import daos.PlayerTokenDAO
import loggers.MathBotLogger
import play.api.Environment
import play.api.libs.ws.WSClient

object LevelActor {
  def props(out: ActorRef, playerTokenDAO: PlayerTokenDAO, ws: WSClient, environment: Environment) =
    Props(new LevelActor(out, playerTokenDAO, ws, environment))
}

class LevelActor @Inject()(out: ActorRef, playerTokenDAO: PlayerTokenDAO, ws: WSClient, environment: Environment)
    extends Actor {
  import LevelActor._
  import context.dispatcher

  private final val className = "LevelActor"

  override def receive: Receive = {
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
