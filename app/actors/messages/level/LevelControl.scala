package actors.messages.level
import akka.http.scaladsl.util.FastFuture
import com.google.inject.Inject
import daos.{FunctionsDAO, PlayerTokenDAO, StatsDAO}
import level_gen.SuperClusters
import level_gen.models.CelestialSystem
import types.TokenId

import scala.concurrent.{ExecutionContext, Future}

class LevelControl @Inject()(
    statsDAO: StatsDAO,
    functionsDAO: FunctionsDAO,
    playerTokenDAO: PlayerTokenDAO
)(implicit ec: ExecutionContext) {
  final val superCluster: CelestialSystem = SuperClusters.getCluster("SuperCluster1")
  /*
   * Checks if levels have been added or removed then updates the stats
   * */
  private def updateStats(stats: Stats): Future[Stats] = {
    val shouldMatch = Stats("").list.toList
    if (stats.list.toList.length != shouldMatch.length) {
      val filteredUserStats = stats.list.filter(s => shouldMatch.exists(_._1 == s._1))
      val shouldMatchDiff = shouldMatch.diff(filteredUserStats.toList).toMap
      val updatedUserStats = stats.copy(list = filteredUserStats ++ shouldMatchDiff)
      statsDAO.replace(updatedUserStats)
    } else FastFuture.successful(stats)
  }

  /*
   * Assembles galaxy data back into its nested data structure
   * */
  def getGalaxyData(tokenId: TokenId, path: Option[String]): Future[GalaxyData] = {
    statsDAO.findStats(tokenId).flatMap {
      case Some(stats) =>
        FastFuture.successful(GalaxyData(stats, path.getOrElse(stats.currentPath)))
      case None => getStats(tokenId).map(stats => GalaxyData(stats, path.getOrElse("00000")))
    }
  }

  /*
   * !! Possibly not needed !!
   * Assembles star system data back into its nested data structure
   * */
  def getStarSystemData(tokenId: TokenId, path: Option[String]): Future[StarSystemData] = {
    statsDAO.findStats(tokenId).flatMap {
      case Some(stats) =>
        FastFuture.successful(StarSystemData(stats, path.getOrElse(stats.currentPath)))
      case None => getStats(tokenId).map(stats => StarSystemData(stats, path.getOrElse("00000")))
    }
  }

  def getPath(tokenId: TokenId): Future[String] = {
    for {
      stats <- getStats(tokenId)
    } yield stats.currentPath
  }

  /*
   * Creates built continent ready for the client to render
   * also includes functions for that continent
   * */
  private def createBuiltContinent(tokenId: TokenId, path: String): Future[BuiltContinent] = {
    getFunctions(tokenId).map { functions =>
      val continent = getCelestialSystem(path)
      statsDAO.updateCurrentLevel(tokenId, path)
      val builtContinent = BuiltContinent(functions, continent)
      functionsDAO.updateFunction(tokenId, builtContinent.lambdas.main)
      builtContinent
    }
  }

  def getCelestialSystem(p: String): CelestialSystem = {
    val path = Stats.makePath(p)
    superCluster
      .children(path(1))
      .children(path(2))
      .children(path(3))
      .children(path.drop(4).mkString("").toInt)
  }

  def getVideoIds(path: String): List[String] = {
    getCelestialSystem(path).continentStruct.map(_.videoHints).getOrElse(List.empty[String])
  }

  /*
   * Gets all functions for user
   * */
  private def getFunctions(tokenId: TokenId): Future[Functions] = {
    for {
      playerToken <- playerTokenDAO.getToken(tokenId)
      functions <- functionsDAO.find(tokenId)
    } yield
      functions match {
        case Some(f) => f // already in new system
        case None =>
          playerToken match {
            case Some(models.PlayerToken(_, lambdas, _, _, _)) if lambdas.isDefined => // legacy account
              val functions: Functions = Functions(tokenId, lambdas.get)
              functionsDAO.insert(functions)
              functions
            case _ => // new account
              val functions: Functions = Functions(tokenId)
              functionsDAO.insert(functions)
              functions
          }
      }
  }

  def updateFunction(tokenId: TokenId, function: Function): Future[Function] =
    for {
      _ <- functionsDAO.updateFunction(tokenId, function)
    } yield function

  /*
   * Gets a users stats
   * */
  def getStats(tokenId: TokenId): Future[Stats] = {
    (for {
      stats <- statsDAO.findStats(tokenId)
      playerToken <- playerTokenDAO.getToken(tokenId)
    } yield
      stats match {
        case Some(s) => // user already in new system
          updateStats(s)
        case None =>
          FastFuture.successful(
            playerToken match {
              case Some(p) => // legacy user needs moved
                val swappedStats = Stats(tokenId, p.stats.get)
                statsDAO.insert(swappedStats)
                swappedStats
              case None => // new user
                val stats = Stats(tokenId)
                statsDAO.insert(stats)
                stats
            }
          )
      }).flatMap(s => s)
  }

  /*
   * Gets the continent data and current path
   * If path is omitted the current path in the db will be used
   * This is the main function used by the outside to get a built continent
   * */
  def getBuiltContinent(tokenId: TokenId, pathOpt: Option[String] = None): Future[PathAndContinent] = {
    for {
      stats <- getStats(tokenId)
      continent <- createBuiltContinent(tokenId, pathOpt.getOrElse(stats.currentPath))
    } yield PathAndContinent(pathOpt.getOrElse(stats.currentPath), continent)
  }

  /*
   * Advances the user stats to the next level if success is true otherwise
   * updates times played and last played.
   * */
  def advanceStats(tokenId: TokenId, success: Boolean): Future[PathAndContinent] = {
    for {
      _ <- getStats(tokenId) // ensures user definitely exists in table
      newPath <- statsDAO.incrementWinsAndTimedPlayed(tokenId, success)
      continent <- createBuiltContinent(tokenId, newPath)
    } yield PathAndContinent(newPath, continent)
  }

  def updatePath(tokenId: TokenId, path: String) =
    for {
      updated <- statsDAO.updatePath(tokenId, path)
    } yield path
}
