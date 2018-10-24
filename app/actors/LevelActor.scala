package actors

import actors.messages.ActorFailed
import actors.messages.level._
import akka.actor.{Actor, ActorRef, Props}
import akka.japi.Option.Some
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
  final case class GetStarSystemData(tokenId: TokenId, path: StarSystemId)
  final case class GetPlanetData(tokenId: TokenId)
  final case class ChangeLevel(tokenId: TokenId, path: String)
  final case class UpdateStats(tokenId: TokenId, success: Boolean)
  final case class CreateContinentData(functions: Functions, path: String)
  final case class UpdateFunction(tokenId: TokenId, function: Function)
  final case class CreateStatsAndContinent(tokenId: TokenId)
  final case class GetContinentData(tokenId: TokenId, path: ContinentId)

  final case class RunWon(tokenId: TokenId, success: Boolean)

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
     * not actually useful for app, get galaxy data and get star system data will both update or add stats
     * without this.
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
      for {
        galaxyData <- levelControl.getGalaxyData(tokenId, path)
      } yield out ! galaxyData
    /*
     * Get star system data and all of its children.
     * Planets data child's contain the continents stats and id for rendering in the profile page.
     * */
    case GetStarSystemData(tokenId, path) =>
      for {
        starSystemData <- levelControl.getStarSystemData(tokenId, path)
      } yield out ! starSystemData
    /*
     * Gets the built continent data for loading a continent.
     * Used when client clicks a new continent
     * */
    case GetContinentData(tokenId, path) =>
      for {
        continent <- levelControl.createBuiltContinent(tokenId, path)
      } yield out ! continent
    /*
     * For testing update stats without running compiler
     * */
    case RunWon(tokenId, success) =>
      levelControl
        .updateStats(tokenId, success)
        .map(pathAndContinent => out ! pathAndContinent)
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
