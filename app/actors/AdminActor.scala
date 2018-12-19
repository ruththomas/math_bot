package actors
import actors.messages.ActorFailed
import actors.messages.admin.{AdminEvent, CurrentPath, LevelStats, NewEvent}
import actors.messages.playeraccount.{MaxLevel, UserAccountSignups}
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import daos._
import play.api.Environment
import play.api.libs.ws.WSClient

object AdminActor {
  final case class GetUserCount()
  final case class GetMaxLevel()
  final case class GetEvents()
  final case class PutEvent(event: Option[AdminEvent])
  final case class PostEvent(event: Option[NewEvent])
  final case class DeleteEvent(event: Option[AdminEvent])

  final case class DeleteEventResult(result: String)
  final case class UserMaxLevel(maxLevel: Seq[MaxLevel])
  final case class GetActiveUserCount()
  final case class GetSignupsPerDay()
  final case class GetLoginsLast7Days()
  final case class GetLevelStats(level: Option[String])
  final case class GetCurrentPath()
  final case class CurrentPathResult(currentPaths: Seq[CurrentPath])
  final case class LevelStatsResult(levelStats: Seq[LevelStats])
  final case class SignupsPerDay(result: Seq[UserAccountSignups])
  final case class Last7DaysLogins(logins: Long)
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
      playerAccountDAO.maxLevelStats.map { maxLevels =>
        out ! UserMaxLevel(maxLevels)
      }

    case GetLoginsLast7Days() =>
      playerAccountDAO.last7DaysLoginCount.map { logins =>
        out ! Last7DaysLogins(logins)
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

    case GetCurrentPath() =>
      statsDAO.currentPath.map { currentPaths =>
        out ! CurrentPathResult(currentPaths)
      }
    case GetLevelStats(level) =>
      statsDAO.levelStats(level).map { levelStats =>
        out ! LevelStatsResult(levelStats)
      }
    case GetUserCount() =>
      for {
        migratedCount <- auth0LegacyDao.countUnmigrated
        playerAccount <- playerAccountDAO.count

      } yield out ! UserCount(playerAccount + migratedCount)

    case GetEvents() =>
      eventsDAO.getEvents.map { events =>
        out ! Events(events)
      }

    case PutEvent(event) =>
      event match {

        case Some(adminEvent) =>
          eventsDAO.replace(adminEvent).map { event_ =>
            out ! Event(event_)
          }
        case _ => out ! ActorFailed("Invalid request")

      }

    case DeleteEvent(event) =>
      event match {

        case Some(adminEvent) =>
          eventsDAO.remove(adminEvent.id)

          out ! Event(adminEvent)

        case _ => out ! ActorFailed("Invalid Request")
      }

    case PostEvent(event) =>
      event match {

        case Some(adminEvent) =>
          val newEvent = AdminEvent(adminEvent.date, adminEvent.title, adminEvent.description, adminEvent.links)
          eventsDAO.insert(newEvent).map { evt =>
            out ! Event(evt)
          }
        case _ => out ! ActorFailed
      }

    case actorFailed: ActorFailed => out ! actorFailed
  }
}
