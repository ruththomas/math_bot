package actors.messages.level
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

  def createBuiltContinent(tokenId: TokenId, p: String): Future[BuiltContinent] = getFunctions(tokenId).map {
    functions =>
      val path = Stats.makePath(p)
      val struct = superCluster
        .children(path(1))
        .children(path(2))
        .children(path(3))
        .children(path.drop(4).mkString("").toInt)
        .continentStruct
        .get
      BuiltContinent(functions, struct)
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
    for {
      stats <- statsDAO.findStats(tokenId)
      playerToken <- playerTokenDAO.getToken(tokenId)
    } yield
      stats match {
        case Some(s) => s // user already in new system
        case None =>
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
      }
  }

  def getStatsAndContinent(tokenId: TokenId): Future[StatsAndContinent] = {
    for {
      stats <- getStats(tokenId)
      continent <- createBuiltContinent(tokenId, stats.superClusterPath)
    } yield StatsAndContinent(stats, continent)
  }

  def updateStats(tokenId: TokenId, success: Boolean): Future[StatsAndContinent] = {
    if (success) {
      ???
    } else {
      ???
    }
  }
}
