package actors
import actors.messages.ActorFailed
import actors.messages.playeraccount.UserAccountSignups
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import daos.{Auth0LegacyDao, PlayerAccountDAO, PlayerTokenDAO, SessionDAO}
import play.api.Environment
import play.api.libs.ws.WSClient

object AdminActor {
  final case class GetUserCount()
  final case class GetActiveUserCount()
  final case class GetSignupsPerDay()
  final case class GetLoginsLast7Days()
  final case class SignupsPerDay(result: Seq[UserAccountSignups])
  final case class Last7DaysLogins(logins: Long)
  final case class UserCount(count: Long)
  final case class ActiveUserCount(count: Long)

  def props(out: ActorRef,
            playerAccountDAO: PlayerAccountDAO,
            playerTokenDAO: PlayerTokenDAO,
            auth0LegacyDao: Auth0LegacyDao,
            sessionDao: SessionDAO,
            ws: WSClient,
            environment: Environment) =
    Props(new AdminActor(out, playerAccountDAO, playerTokenDAO, auth0LegacyDao, sessionDao, ws, environment))
}

class AdminActor @Inject()(out: ActorRef,
                           playerAccountDAO: PlayerAccountDAO,
                           playerTokenDAO: PlayerTokenDAO,
                           auth0LegacyDao: Auth0LegacyDao,
                           sessionDAO: SessionDAO,
                           ws: WSClient,
                           environment: Environment)
    extends Actor {

  import AdminActor._
  import context.dispatcher

  private final val className = "AdminActor"

  override def receive: Receive = {

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

      sessionDAO.count.map {
        count => out ! ActiveUserCount(count)
      }
    case GetUserCount() =>
      for {
        migratedCount <- auth0LegacyDao.countUnmigrated
        playerAccount <- playerAccountDAO.count

      } yield out ! UserCount(playerAccount + migratedCount)

    case actorFailed: ActorFailed => out ! actorFailed
  }
}
