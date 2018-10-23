package actors

import actors.messages.ActorFailed
import actors.messages.level._
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import daos.{FunctionsDAO, PlayerTokenDAO, StatsDAO}
import level_gen.SuperClusters
import play.api.Environment
import play.api.libs.ws.WSClient
import types._

object LevelActor {
  final case class GetSuperCluster(name: String)
  final case class GetLambdas(tokenId: TokenId)
  final case class GetStats(tokenId: TokenId)
  final case class GetSuperClusterData(tokenId: TokenId)
  final case class GetGalaxyData(tokenId: TokenId, path: String)
  final case class GetStarSystemData(tokenId: TokenId, key: StarSystemId)
  final case class GetPlanetData(tokenId: TokenId)
  final case class GetContinentData(tokenId: TokenId, key: ContinentId)
  final case class ChangeLevel(tokenId: TokenId, path: String)
  final case class UpdateStats(tokenId: TokenId, success: Boolean)
  final case class CreateContinentData(functions: Functions, key: String)
  final case class UpdateFunction(tokenId: TokenId, function: Function)
  final case class CreateStatsAndContinent(tokenId: TokenId)

  final case class RunWon(tokenId: TokenId)

  def props(out: ActorRef,
            statsDAO: StatsDAO,
            lambdasDAO: FunctionsDAO,
            playerTokenDAO: PlayerTokenDAO,
            ws: WSClient,
            environment: Environment,
            levelControl: LevelControl) =
    Props(new LevelActor(out, statsDAO, lambdasDAO, playerTokenDAO, ws, environment, levelControl))
}

class LevelActor @Inject()(out: ActorRef,
                           statsDAO: StatsDAO,
                           functionsDAO: FunctionsDAO,
                           playerTokenDAO: PlayerTokenDAO,
                           ws: WSClient,
                           environment: Environment,
                           levelControl: LevelControl)
    extends Actor {
  import LevelActor._
  import context.dispatcher

  private final val className = "LevelActor"

  override def receive: Receive = {
    /*
     * Gets entire game layout, not actually useful for the game, just for testing.
     * */
    case GetSuperCluster(_) =>
      out ! SuperClusters.getCluster("SuperCluster1")
    /*
     * Get users stats
     * */
    case GetStats(tokenId) =>
      for {
        stats <- levelControl.getStats(tokenId)
      } yield out ! stats
    /*
     * Get galaxy data, children is a list of the star system data without the continents.
     * Use the star system id to get the star system and all its children
     * */
    case GetGalaxyData(tokenId, path) =>
      statsDAO.gatherGalaxy(tokenId, path).map {
        case Some(data) =>
          val galaxy = data("galaxy").head
          statsDAO.updateCurrentLevel(tokenId, path)
          out ! GalaxyData(
            id = galaxy._1,
            starSystems = data("starSystems").toList.sortBy(_._1.substring(3)).map { s =>
              StarSystemData(s._1, s._2, None)
            }
          )
        case None => self ! ActorFailed(s"Unable to locate stats for $tokenId")
      }
    /*
     * Get star system data and all of its children.
     * Planets data child's contain the continents stats and id for rendering in the profile page.
     * */
    case GetStarSystemData(tokenId, path) =>
      statsDAO.gatherStarSystem(tokenId, path).map {
        case Some(data) =>
          val continents = data("continents")
          val planets = data("planets").toList.sortBy(_._1.last.toInt).map { p =>
            PlanetData(
              id = p._1,
              stats = p._2,
              continents.filterKeys(_.take(4).contains(p._1)).toList.sortBy(_._1.substring(4).toInt).map { c =>
                ContinentData(c._1, c._2)
              }
            )
          }
          val starSystem = data("starSystem").head
          statsDAO.updateCurrentLevel(tokenId, path)
          out ! StarSystemData(
            starSystem._1,
            starSystem._2,
            Some(planets)
          )
        case None => self ! ActorFailed(s"Unable to locate stats for $tokenId")
      }
    /*
     * Gets the built continent data for loading a continent.
     * */
    case GetContinentData(tokenId, path) =>
      for {
        builtContinent <- levelControl.createBuiltContinent(tokenId, path)
      } yield out ! builtContinent
    /*
     *
     * */
    case RunWon(tokenId) =>
      levelControl
        .updateStats(tokenId, success = true)
        .map(statsAndContinent => out ! statsAndContinent.stats.currentPath)
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
