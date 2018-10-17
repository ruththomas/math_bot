package actors

import actors.messages.ActorFailed
import actors.messages.level._
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import daos.{LambdasDAO, PlayerTokenDAO, StatsDAO}
import level_gen.SuperClusters
import play.api.Environment
import play.api.libs.ws.WSClient
import types._

object LevelActor {
  final case class GetSuperCluster(name: String)
  final case class GetLambdas(tokenId: TokenId)
  final case class GetStats(tokenId: TokenId)
  final case class GetSuperClusterData(tokenId: TokenId)
  final case class GetGalaxyData(tokenId: TokenId)
  final case class GetStarSystemData(tokenId: TokenId, key: StarSystemId)
  final case class GetPlanetData(tokenId: TokenId)
  final case class GetContinentData(tokenId: TokenId, key: ContinentId)
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
      statsDAO.findStats(tokenId).map {
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
    case GetGalaxyData(tokenId) =>
      statsDAO.gatherGalaxy(tokenId, "00").map {
        case Some(data) =>
          val galaxy = data("galaxy").head
          out ! GalaxyData(
            id = galaxy._1,
            starSystems = data("starSystems").toList.sortBy(_._1.substring(3)).map { s =>
              StarSystemData(s._1, s._2, None)
            }
          )
        case None => self ! ActorFailed(s"Unable to locate stats for $tokenId")
      }
    case GetStarSystemData(tokenId, key) =>
      statsDAO.gatherStarSystem(tokenId, key).map {
        case Some(data) =>
          val continents = data("continents")
          val planets = data("planets").toList.sortBy(_._1.last.toInt).map { p =>
            PlanetData(
              id = p._1,
              stats = p._2,
              continents =
                continents.filterKeys(_.take(4).contains(p._1)).toList.sortBy(_._1.substring(4).toInt).map { c =>
                  ContinentData(c._1, c._2)
                }
            )
          }
          val starSystem = data("starSystem").head
          out ! StarSystemData(
            starSystem._1,
            starSystem._2,
            Some(planets)
          )
        case None => self ! ActorFailed(s"Unable to locate stats for $tokenId")
      }
    case GetContinentData(tokenId, key) =>
      for {
        playerToken <- playerTokenDAO.getToken(tokenId)
      } yield {
        val superCluster = SuperClusters.getCluster("SuperCluster1")
        val path = Stats.makePath(key)
        val struct = superCluster
          .children(path(0))
          .children(path(1))
          .children(path(2))
          .children(path(3))
          .continentStruct
          .get
        out ! BuiltContinent(
          lambdas = playerToken.get.lambdas.get,
          continentStruct = struct
        )
      }
    case HandleWin(tokenId) => ???
    case HandleLoss(tokenId) => ???
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
