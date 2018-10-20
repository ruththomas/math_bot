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
  final case class GetGalaxyData(tokenId: TokenId)
  final case class GetStarSystemData(tokenId: TokenId, key: StarSystemId)
  final case class GetPlanetData(tokenId: TokenId)
  final case class GetContinentData(tokenId: TokenId, key: ContinentId)
  final case class ChangeLevel(tokenId: TokenId, starSystemInd: Int, planetInd: Int, continentInd: Int)
  final case class HandleWin(tokenId: TokenId)
  final case class HandleLoss(tokenId: TokenId)
  final case class CreateContinentData(functions: Functions, key: String)
  final case class UpdateFunction(tokenId: TokenId, function: Function)

  private final val galaxyLabel: String = "galaxy"
  private final val starSystemLabel: String = "starSystem"
  private final val planetLabel: String = "planet"
  private final val continentLabel: String = "continent"

  def props(out: ActorRef,
            statsDAO: StatsDAO,
            lambdasDAO: FunctionsDAO,
            playerTokenDAO: PlayerTokenDAO,
            ws: WSClient,
            environment: Environment) =
    Props(new LevelActor(out, statsDAO, lambdasDAO, playerTokenDAO, ws, environment))
}

class LevelActor @Inject()(out: ActorRef,
                           statsDAO: StatsDAO,
                           functionsDAO: FunctionsDAO,
                           playerTokenDAO: PlayerTokenDAO,
                           ws: WSClient,
                           environment: Environment)
    extends Actor {
  import LevelActor._
  import context.dispatcher

  private final val className = "LevelActor"

  override def receive: Receive = {
    /*
     * Gets entire game layout, not actually useful for the game, just for testing.
     * */
    case GetSuperCluster(name) =>
      out ! SuperClusters.getCluster("SuperCluster1")
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
    /*
     * Get galaxy data, children is a list of the star system data without the continents.
     * Use the star system id to get the star system and all its children
     * */
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
    /*
     * Get star system data and all of its children.
     * Planets data child's contain the continents stats and id for rendering in the profile page.
     * */
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
    /*
     * Gets the built continent data for loading a continent.
     * */
    case GetContinentData(tokenId, key) =>
      functionsDAO.find(tokenId).map {
        case Some(functions) => // already in new system
          self ! CreateContinentData(functions, key)
        case None =>
          playerTokenDAO.getToken(tokenId).map {
            case Some(models.PlayerToken(_, lambdas, _, _, _)) if lambdas.isDefined => // legacy account
              val swappedFunctions: Functions = Functions(tokenId, lambdas.get)
              functionsDAO.insert(swappedFunctions)
              self ! CreateContinentData(swappedFunctions, key)
            case _ => // new account
              val functions: Functions = Functions(tokenId)
              functionsDAO.insert(functions)
              self ! CreateContinentData(functions, key)
          }
      }
    case CreateContinentData(functions, key) =>
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
        functions = functions,
        continentStruct = struct
      )
    case UpdateFunction(tokenId, function) =>
      functionsDAO.updateFunction(tokenId, function).map {
        case Some(_) =>
          val p = 0
          out ! function
        case None => self ! ActorFailed(s"Unable to locate functions for $tokenId")
      }
    case HandleWin(tokenId) => ???
    case HandleLoss(tokenId) => ???
    case actorFailed: ActorFailed => out ! actorFailed
  }
}
