package actors

import actors.messages.ActorFailed
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import com.google.inject.name.Named
import play.api.libs.json.Json

object VideoHintActor {
  def props(out: ActorRef) = Props(new VideoHintActor(out))
}

@Named("video-hint-actor")
class VideoHintActor @Inject()(out: ActorRef) extends Actor {
  import VideoHintActor._

  override def receive: Receive = {
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
