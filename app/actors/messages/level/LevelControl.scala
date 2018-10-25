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

  private def updateStats(stats: Stats): Future[Stats] = {
    val shouldMatch = Stats("").list.toList
    if (stats.list.toList.length != shouldMatch.length) {
      val filteredUserStats = stats.list.filter(s => shouldMatch.exists(_._1 == s._1))
      val shouldMatchDiff = shouldMatch.diff(filteredUserStats.toList).toMap
      val updatedUserStats = stats.copy(list = filteredUserStats ++ shouldMatchDiff)
      statsDAO.replace(updatedUserStats)
    } else FastFuture.successful(stats)
  }

  def getGalaxyData(tokenId: TokenId, path: String): Future[GalaxyData] = {
    statsDAO.updateCurrentLevel(tokenId, path)
    statsDAO.findStats(tokenId).flatMap {
      case Some(stats) => FastFuture.successful(GalaxyData(stats, path))
      case None => getStats(tokenId).map(stats => GalaxyData(stats, path))
    }
  }

  def getStarSystemData(tokenId: TokenId, path: String): Future[StarSystemData] = {
    statsDAO.updateCurrentLevel(tokenId, path)
    statsDAO.findStats(tokenId).flatMap {
      case Some(stats) => FastFuture.successful(StarSystemData(stats, path))
      case None => getStats(tokenId).map(stats => StarSystemData(stats, path))
    }
  }

  def createBuiltContinent(tokenId: TokenId, p: String): Future[BuiltContinent] = getFunctions(tokenId).map {
    functions =>
      val path = Stats.makePath(p)
      val continent = superCluster
        .children(path(1))
        .children(path(2))
        .children(path(3))
        .children(path.drop(4).mkString("").toInt)
      statsDAO.updateCurrentLevel(tokenId, p)
      BuiltContinent(functions, continent)
  }

  def getFunctions(tokenId: TokenId): Future[Functions] = {
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

  def getBuiltContinent(tokenId: TokenId, pathOpt: Option[String] = None): Future[PathAndContinent] = {
    for {
      stats <- getStats(tokenId)
      continent <- createBuiltContinent(tokenId, pathOpt.getOrElse(stats.currentPath))
    } yield PathAndContinent(pathOpt.getOrElse(stats.currentPath), continent)
  }

  def advanceStats(tokenId: TokenId, success: Boolean): Future[PathAndContinent] = {
    for {
      _ <- getStats(tokenId) // ensures user definitely exists in table
      newPath <- statsDAO.incrementWinsAndTimedPlayed(tokenId, success)
      continent <- createBuiltContinent(tokenId, newPath)
    } yield PathAndContinent(newPath, continent)
  }
}
