package actors.messages.level
import actors.messages.AssignedFunction
import akka.http.scaladsl.util.FastFuture
import com.google.inject.Inject
import daos.{FunctionsDAO, PlayerTokenDAO, StatsDAO}
import level_gen.SuperClusters
import level_gen.models.{CelestialSystem, ContinentStruct}
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
      val continent = getContinentData(path)
      val collectedPreBuilts = gatherAssignedStagedAndPrebuiltActive(path)
      statsDAO.updateCurrentLevel(tokenId, path)
      val builtContinent =
        BuiltContinent(
          functions,
          continent.copy(
            continentStruct = continent.continentStruct.map(
              cs => cs.copy(preBuiltActive = collectedPreBuilts._1, assignedStaged = collectedPreBuilts._2)
            )
          ),
          Some(functionsDAO)
        )
      builtContinent
    }
  }

  /*
   * gathers all pre built actives and assigned staged
   * this function adds all the previous levels functions to the current
   * */
  private def gatherAssignedStagedAndPrebuiltActive(path: String): (List[AssignedFunction], List[AssignedFunction]) = {
    val bothLast = getLastPreBuiltActiveAndAssignedStaged(path)
    val allPrebuiltActives = getAllPreBuiltActives()
    val allAssignedStaged = getAllAssignedStaged()
    (
      bothLast._1 match {
        case Some(active) => allPrebuiltActives.takeWhile(_.createdId != active.createdId) :+ active
        case None => Nil
      },
      bothLast._2 match {
        case Some(preBuilt) => allAssignedStaged.takeWhile(_.createdId != preBuilt.createdId) :+ preBuilt
        case None => Nil
      }
    )
  }

  /*
   * Gets the last assigned staged and pre built active for passed in path
   * */
  private def getLastPreBuiltActiveAndAssignedStaged(
      path: String
  ): (Option[AssignedFunction], Option[AssignedFunction]) = {
    val planet = getPlanetData(path)
    val p = 0
    (
      planet.children.flatMap(_.continentStruct.get.preBuiltActive).lastOption,
      planet.children.flatMap(_.continentStruct.get.assignedStaged).lastOption
    )
  }

  /*
   * Gets all the pre built actives for every level
   * */
  private def getAllPreBuiltActives(celestialSystem: CelestialSystem = superCluster): List[AssignedFunction] =
    celestialSystem.continentStruct match {
      case Some(continentStruct) =>
        continentStruct.preBuiltActive ::: celestialSystem.children.flatMap(getAllPreBuiltActives)
      case None => celestialSystem.children.flatMap(getAllPreBuiltActives)
    }

  /*
   * Gets all the assigned staged for every level
   * */
  private def getAllAssignedStaged(celestialSystem: CelestialSystem = superCluster): List[AssignedFunction] =
    celestialSystem.continentStruct match {
      case Some(continentStruct) =>
        continentStruct.assignedStaged ::: celestialSystem.children.flatMap(getAllAssignedStaged)
      case None => celestialSystem.children.flatMap(getAllAssignedStaged)
    }

  def getPlanetData(p: String): CelestialSystem = {
    val path = Stats.makePath(p)
    superCluster.children(path(1)).children(path(2)).children(path(3))
  }

  def getContinentData(p: String): CelestialSystem = {
    val path = Stats.makePath(p)
    superCluster
      .children(path(1))
      .children(path(2))
      .children(path(3))
      .children(path.drop(4).mkString("").toInt)
  }

  def getVideoIds(path: String): List[String] = {
    getContinentData(path).continentStruct.map(_.videoHints).getOrElse(List.empty[String])
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
              val functions: Functions =
                Functions(tokenId, lambdas.get, getAllAssignedStaged() ::: getAllPreBuiltActives())
              functionsDAO.insert(functions)
              functions
            case _ => // new account
              val functions: Functions = Functions(tokenId)
              functionsDAO.insert(functions)
              functions
          }
      }
  }

  /*
   * Updates function at the top level, only good when adding function to func property
   * this is the most efficient way to update a function.
   * Not good for updating color, name, or displayImage. Use updateFunctionProperties instead.
   * */
  def updateFunction(tokenId: TokenId, function: Function): Future[Function] =
    for {
      _ <- functionsDAO.updateFunction(tokenId, function)
    } yield function

  /*
   * Updates all instances of passed in functions then returns updated PathAndContinent
   * for client to update all instances of function
   * */
  def updateFunctionProperties(tokenId: TokenId, function: Function): Future[PathAndContinent] = {
    for {
      functions <- getFunctions(tokenId)
      _ <- functionsDAO.replaceAll(
        tokenId,
        Functions(
          tokenId,
          changedAllInstances(functions.listed, function)
        )
      )
      pathAndContinent <- getBuiltContinent(tokenId)
    } yield pathAndContinent
  }

  /*
   * Recursively updates all instances of a functions color, name, (todo displayImage)
   * */
  private def changedAllInstances(functions: List[Function], function: Function): List[Function] = {
    functions.map { ft =>
      val func = ft.func.getOrElse(List.empty[Function])
      ft.copy(
        name = if (ft.created_id == function.created_id) function.name else ft.name,
        color = if (ft.created_id == function.created_id) function.color else ft.color,
        displayName =
          if (ft.created_id == function.created_id) Some(function.displayName.getOrElse(false)) else ft.displayName,
        func = Some(changedAllInstances(func, function))
      )
    }
  }

  def activateDeactivateFunction(tokenId: TokenId, function: Function): Future[PreparedFunctions] = {
    for {
      functions <- getFunctions(tokenId)
      path <- getPath(tokenId)
    } yield PreparedFunctions(functions, getContinentData(path).continentStruct.get, function, functionsDAO)
  }

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

  def updatePath(tokenId: TokenId, path: String): Future[String] =
    for {
      _ <- statsDAO.updatePath(tokenId, path)
    } yield path

  def unlock(tokenId: TokenId): Future[Stats] = {
    for {
      _ <- getStats(tokenId)
      unlocked <- statsDAO.unlock(tokenId)
    } yield unlocked
  }
}
