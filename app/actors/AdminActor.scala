package actors
import actors.messages.ActorFailed
import actors.messages.admin.{AdminEvent, LevelStats, NewEvent}
import actors.messages.playeraccount.{MaxLevel, UserAccountSignups}
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import daos._
import play.api.Environment
import play.api.libs.ws.WSClient

abstract class EventResult {
  def result: String
}

object AdminActor {
  final case class GetUserCount()
  final case class GetMaxLevel()
  final case class GetEvents()
  final case class PutEvent(event: Option[AdminEvent])
  final case class PostEvent(event: Option[NewEvent])
  final case class DeleteEvent(event: Option[AdminEvent])
  final case class DeleteEventResult(result: String) extends EventResult
  final case class PostEventResult(result: String) extends EventResult
  final case class PutEventResult(result: String) extends EventResult
  final case class UserMaxLevel(maxLevel: Seq[MaxLevel])
  final case class GetActiveUserCount()
  final case class GetSignupsPerDay()
  final case class GetLoginsLastXDays(days: Option[Int])
  final case class GetLevelStats(level: Option[String])
  final case class LevelStatsResult(levelStats: Seq[LevelStats])
  final case class SignupsPerDay(result: Seq[UserAccountSignups])
  final case class LastXDaysLogins(logins: Long)
  final case class UserCount(count: Long)
  final case class ActiveUserCount(count: Long)
  final case class Events(events: Seq[AdminEvent])
  final case class Event(event: AdminEvent)

  def props(out: ActorRef,
            playerAccountDAO: PlayerAccountDAO,
            playerTokenDAO: PlayerTokenDAO,
            auth0LegacyDao: Auth0LegacyDao,
            sessionDao: SessionDAO,
            statsDAO: StatsDAO,
            eventsDAO: EventsDAO,
            ws: WSClient,
            environment: Environment) =
    Props(
      new AdminActor(out,
                     playerAccountDAO,
                     playerTokenDAO,
                     auth0LegacyDao,
                     sessionDao,
                     statsDAO,
                     eventsDAO,
                     ws,
                     environment)
    )
}

class AdminActor @Inject()(out: ActorRef,
                           playerAccountDAO: PlayerAccountDAO,
                           playerTokenDAO: PlayerTokenDAO,
                           auth0LegacyDao: Auth0LegacyDao,
                           sessionDAO: SessionDAO,
                           statsDAO: StatsDAO,
                           eventsDAO: EventsDAO,
                           ws: WSClient,
                           environment: Environment)
    extends Actor {

  import AdminActor._
  import context.dispatcher

  private final val className = "AdminActor"

  override def receive: Receive = {

    case GetMaxLevel() =>
      statsDAO.maxLevelStats.map { maxLevels =>
        out ! UserMaxLevel(maxLevels)
      }
    case GetLoginsLastXDays(days) =>
      playerAccountDAO.lastXDaysLoginCount(days).map { logins =>
        out ! LastXDaysLogins(logins)
      }
    case GetSignupsPerDay() =>
      playerAccountDAO.signupsPerDay
        .map { signups =>
          out ! SignupsPerDay(signups)
        }
    case GetActiveUserCount() =>
      sessionDAO.count.map { count =>
        out ! ActiveUserCount(count)
      }
    case GetLevelStats(level) =>
      statsDAO.levelStats(level).map { levelStats =>
        out ! LevelStatsResult(levelStats)
      }
    case GetUserCount() =>
      for {
        unMigratedUserCount <- auth0LegacyDao.countUnmigrated
        userCount <- playerAccountDAO.userCount
      } yield out ! UserCount(userCount + unMigratedUserCount)
    case GetEvents() =>
      eventsDAO.getEvents.map { events =>
        out ! Events(events)
      }
    case PutEvent(event) =>
      event match {
        case Some(adminEvent) =>
          eventsDAO.replace(adminEvent).map { event_ =>
            out ! PutEventResult("successfully replaced event: " + event_.id)
          }
        case _ => out ! ActorFailed("Invalid request")

      }
    case DeleteEvent(event) =>
      event match {
        case Some(adminEvent) =>
          eventsDAO.remove(adminEvent.id)

          out ! DeleteEventResult("successfully removed event: " + adminEvent.id)
        case _ => out ! ActorFailed("Invalid Request")
      }
    case PostEvent(event) =>
      event match {
        case Some(adminEvent) =>
          val newEvent = AdminEvent(adminEvent.date, adminEvent.title, adminEvent.description, adminEvent.links)
          eventsDAO.insert(newEvent).map { evt =>
            out ! PostEventResult("successfully added event: " + evt.id)
          }
        case _ => out ! ActorFailed("Invalid Request")
      }

    case actorFailed: ActorFailed => out ! actorFailed
  }
}
