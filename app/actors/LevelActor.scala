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
  final case class GetLambdas()
  final case class GetStats()
  final case class GetSuperClusterData()
  final case class GetGalaxyData(pathOpt: Option[String])
  final case class GetStarSystemData(pathOpt: Option[StarSystemId])
  final case class GetPlanetData()
  final case class ChangeLevel(path: String)
  final case class UpdateStats()
  final case class CreateContinentData(functions: Functions, path: String)
  final case class UpdateFunction(functions: Function)
  final case class CreateStatsAndContinent()
  final case class GetContinentData(pathOpt: Option[ContinentId])
  final case class GetPath()
  final case class RunWon(success: Boolean)
  final case class Init()
  final case class WatchedVideo(id: String, pathOpt: Option[String])
  final case class ResetVideos(id: String, path: String)

  def props(out: ActorRef,
            tokenId: TokenId,
            statsDAO: StatsDAO,
            lambdasDAO: FunctionsDAO,
            playerTokenDAO: PlayerTokenDAO,
            ws: WSClient,
            environment: Environment,
            levelControl: LevelControl) =
    Props(new LevelActor(out, tokenId, statsDAO, lambdasDAO, playerTokenDAO, ws, environment, levelControl))
}

class LevelActor @Inject()(out: ActorRef,
                           tokenId: TokenId,
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
     * not actually useful for app, get galaxy data and get star system data will both update or add stats
     * without this.
     * */
    case GetStats() =>
      for {
        stats <- levelControl.getStats(tokenId)
      } yield out ! stats
    /*
     * Simply gets the users current path
     * */
    case GetPath() =>
      for {
        path <- levelControl.getPath(tokenId)
      } yield out ! path
    /*
     * Get galaxy data, gets star systems and their planets.
     * The planets continents is a list of the continent ids and stats.
     * */
    case GetGalaxyData(pathOpt) =>
      for {
        galaxyData <- levelControl.getGalaxyData(tokenId, pathOpt)
      } yield out ! galaxyData
    /*
     * @deprecated (maybe) this may be unnecessary sine get get galaxy data now includes this.
     * Get star system data and all of its children.
     * Planets data child's contain the continents stats and id for rendering in the profile page.
     * */
    case GetStarSystemData(pathOpt) =>
      for {
        starSystemData <- levelControl.getStarSystemData(tokenId, pathOpt)
      } yield out ! starSystemData
    /*
     * Gets the built continent data for loading a continent.
     * Used when client clicks a new continent
     * */
    case GetContinentData(pathOpt) =>
      for {
        continent <- levelControl.getBuiltContinent(tokenId, pathOpt)
      } yield out ! continent
    /*
     * For testing update stats without running compiler
     * */
    case RunWon(success) =>
      levelControl
        .advanceStats(tokenId, success)
        .map(pathAndContinent => out ! pathAndContinent)
    /*
     * Used for initializing game after authentication
     * */
    case Init() =>
      for {
        _ <- levelControl.getStats(tokenId) // this has to happen first to ensure stats added once
      } yield {
        self ! GetPath()
        self ! GetGalaxyData(None)
        self ! GetContinentData(None)
      }
    case UpdateFunction(function) =>
      for {
        updatedFunction <- levelControl.updateFunction(tokenId, function)
      } yield out ! updatedFunction
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
