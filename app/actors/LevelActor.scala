package actors

import actors.messages.ActorFailed
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import daos.{LambdasDAO, PlayerTokenDAO, StatsDAO}
import level_gen.SuperClusters
import level_gen.models.SuperCluster
import play.api.Environment
import play.api.libs.ws.WSClient
import types._
import messages.level._
import scala.collection.mutable

object LevelActor {
  final case class GetSuperCluster(name: String)
  final case class GetLambdas(tokenId: TokenId)
  final case class GetStats(tokenId: TokenId)
  final case class GetContinent(tokenId: TokenId)
  final case class ChangeLevel(tokenId: TokenId, starSystemInd: Int, planetInd: Int, continentInd: Int)
  final case class HandleWin(tokenId: TokenId)
  final case class HandleLoss(tokenId: TokenId)

  private final val galaxyLabel: String = "galaxy"
  private final val starSystemLabel: String = "starSystem"
  private final val planetLabel: String = "planet"
  private final val continentLabel: String = "continent"

  def props(out: ActorRef,
            statsDAO: StatsDAO,
            lambdasDAO: LambdasDAO,
            playerTokenDAO: PlayerTokenDAO,
            ws: WSClient,
            environment: Environment) =
    Props(new LevelActor(out, statsDAO, lambdasDAO, playerTokenDAO, ws, environment))
}

class LevelActor @Inject()(out: ActorRef,
                           statsDAO: StatsDAO,
                           lambdasDAO: LambdasDAO,
                           playerTokenDAO: PlayerTokenDAO,
                           ws: WSClient,
                           environment: Environment)
    extends Actor {
  import LevelActor._
  import context.dispatcher

  private final val className = "LevelActor"

  override def receive: Receive = {
    /*
     * Gets entire game layout
     * */
    case GetSuperCluster(name) =>
      out ! SuperClusters.getCluster("SuperCluster1")
    case GetLambdas(tokenId) => ???
    /*
     * Get users stats
     * */
    case GetStats(tokenId) =>
      statsDAO.find(tokenId).map {
        case Some(stats) => // if the user already has new stats
          out ! stats
        case None => // else
          // Check if user has a token in the deprecated token table
          playerTokenDAO
            .getToken(tokenId)
            .map {
              case Some(token) => // User exists in token table
                val swappedStats = Stats(tokenId, token.stats.get)
                statsDAO.insert(swappedStats)
                swappedStats
              case None => // else
                val stats = Stats(tokenId)
                statsDAO.insert(stats)
                stats
            }
            .map(out ! _)
      }
    /*
     * Get the current (from stats table) continent step data
     * */
    case GetContinent(tokenId) => // todo - make this return step data
      statsDAO.find(tokenId).map {
        case Some(stats) =>
          out ! SuperClusters
            .getCluster("SuperCluster1")
        case None => self ! ActorFailed(s"Unable to locate stats for $tokenId")
      }
    /*
     * Change the current level and return the step data for the current continent
     * */
    case ChangeLevel(tokenId, starSystemInd, planetInd, continentInd) => // todo - make this return step data
      statsDAO.updateLevel(tokenId, starSystemInd, planetInd, continentInd).map {
        case Some(_) =>
          out ! SuperClusters
            .getCluster("SuperCluster1")
        case None => self ! ActorFailed(s"Unable to update $tokenId's level.")
      }
    case HandleWin(tokenId) => ??? // todo - make this return step data
    case HandleLoss(tokenId) => ???
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
