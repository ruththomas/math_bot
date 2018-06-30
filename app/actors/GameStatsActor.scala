package actors

import actors.messages.ActorFailed
import akka.actor.{Actor, Props}
import akka.pattern.pipe
import loggers.MathBotLogger
import model.PlayerTokenDAO
import play.api.Environment

object GameStatsActor {
  final case class GetTokenCount()

  final case class GameStatsFinished(userCount: Option[String])

  def props(playerTokenDAO: PlayerTokenDAO, logger: MathBotLogger, environment: Environment) =
    Props(new GameStatsActor()(playerTokenDAO, logger, environment))
}

class GameStatsActor()(playerTokenDAO: PlayerTokenDAO, logger: MathBotLogger, environment: Environment) extends Actor {
  import GameStatsActor._
  import context.dispatcher

  private final val className = "GameStatsActor"

  override def receive: Receive = {
    case GetTokenCount =>
      playerTokenDAO.getTokenCount
        .map { count =>
          GameStatsFinished(userCount = Some(count))
        }
        .pipeTo(self)(sender)
    case GameStatsFinished(userCount) => sender ! Left(userCount.getOrElse(0))
    case _ => sender ! Right(ActorFailed("Something went wrong getting game stats"))
  }
}
