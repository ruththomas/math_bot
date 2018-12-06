package actors

import actors.messages.ActorFailed
import actors.messages.level._
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import daos.{FunctionsDAO, PlayerTokenDAO, StatsDAO}
import level_gen.SuperClusters
import play.api.Environment
import play.api.libs.ws.WSClient

object LevelActor {
  final case class GetSuperCluster(name: String)
  final case class GetLambdas()
  final case class GetStats()
  final case class GetSuperClusterData()
  final case class GetGalaxyData(pathOpt: Option[String])
  final case class GetStarSystemData(pathOpt: Option[String])
  final case class GetPlanetData()
  final case class ChangeLevel(path: String)
  final case class UpdateStats()
  final case class CreateContinentData(functions: Functions, path: String)
  final case class UpdateFunction(functions: Function)
  final case class CreateStatsAndContinent()
  final case class GetContinentData(pathOpt: Option[String])
  final case class GetPath()
  final case class RunWon(success: Boolean)
  final case class Init()
  final case class WatchedVideo(id: String, pathOpt: Option[String])
  final case class ResetVideos(id: String, path: String)
  final case class UpdatePath(path: String)
  final case class UpdateFunctionProperties(function: Function)
  final case class ActivateDeactivateFunction(function: Function)
  final case class Unlock()
  final case class ChangeFunctionColor(function: Function)

  def props(out: ActorRef,
            tokenId: String,
            statsDAO: StatsDAO,
            lambdasDAO: FunctionsDAO,
            playerTokenDAO: PlayerTokenDAO,
            ws: WSClient,
            environment: Environment,
            levelControl: LevelControl) =
    Props(new LevelActor(out, tokenId, statsDAO, lambdasDAO, playerTokenDAO, ws, environment, levelControl))
}

class LevelActor @Inject()(out: ActorRef,
                           tokenId: String,
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
    /*
     * Updates top level function (not nest instances of function)
     * this is used when adding functions to the func property
     * not good for color, name, or displayImages changes (use UpdateFunctionProperties instead)
     * */
    case UpdateFunction(function) =>
      for {
        updatedFunction <- levelControl.updateFunction(tokenId, function)
      } yield out ! updatedFunction
    /*
     * Updates color, name, and displayImage of every instance of the passed in function
     * */
    case UpdateFunctionProperties(function) =>
      for {
        updatedPathAndContinent <- levelControl.updateFunctionProperties(tokenId, function)
      } yield out ! updatedPathAndContinent
    case UpdatePath(path) =>
      for {
        _ <- levelControl.updatePath(tokenId, path)
      } yield out ! path
    /*
     * Moves staged to active or active to staged
     * Passed in function must include new category (where function is being moved to)
     * Passed in function muse include new index position (position function should be in list)
     * */
    case ActivateDeactivateFunction(function) =>
      for {
        preparedFunctions <- levelControl.activateDeactivateFunction(tokenId, function)
      } yield out ! preparedFunctions
    /*
     * Updates every instance of a functions color
     * */
    case ChangeFunctionColor(function) =>
      for {
        pathAndContinent <- levelControl.changeFunctionColor(tokenId, function)
      } yield out ! pathAndContinent
    case Unlock() =>
      for {
        updated <- levelControl.unlock(tokenId)
      } yield out ! updated
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
