package actors
import actors.messages.ActorFailed
import akka.actor.{Actor, ActorRef, Props}
import daos.PlayerTokenDAO
import loggers.MathBotLogger
import play.api.Environment
import play.api.libs.ws.WSClient

object AdminActor {
  def props(out: ActorRef, playerTokenDAO: PlayerTokenDAO, ws: WSClient, environment: Environment) =
    Props(new AdminActor()(out, playerTokenDAO, ws, environment))
}

class AdminActor()(out: ActorRef, playerTokenDAO: PlayerTokenDAO, ws: WSClient, environment: Environment)
    extends Actor {
  import AdminActor._
  import context.dispatcher

  private final val className = "AdminActor"

  override def receive: Receive = {
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
