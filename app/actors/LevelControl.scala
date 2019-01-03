package actors

import actors.messages.AssignedFunction
import actors.messages.level._
import actors.messages.playeraccount.{UpdateAccountAccess, UpdateCacheId}
import akka.NotUsed
import akka.actor.ActorRef
import akka.http.scaladsl.util.FastFuture
import akka.stream.scaladsl.Sink
import com.google.inject.Inject
import com.google.inject.name.Named
import daos.{FunctionsDAO, PlayerTokenDAO, StatsDAO}
import level_gen.SuperClusters
import level_gen.models.CelestialSystem
import models.deprecatedPlayerToken.PlayerToken
import level_gen.sandbox.Sandbox._

import scala.concurrent.{ExecutionContext, Future}

class LevelControl @Inject()(
    statsDAO: StatsDAO,
    functionsDAO: FunctionsDAO,
    playerTokenDAO: PlayerTokenDAO,
    @Named(ActorTags.playerAccount) playerAccountActor: ActorRef
)(implicit ec: ExecutionContext) {
  import compiler.ElementKinds._
  final val superCluster: CelestialSystem = SuperClusters.getCluster("SuperCluster1")

  private def nextColor(currentColor: String): String = {
    val ele = listedElements.lift(listedElements.indexWhere(_.name == currentColor) + 1)
    ele.getOrElse(listedElements.head).name
  }

  /*
   * Checks if levels have been added or removed then updates the stats
   * */
  private def updateStats(stats: Stats): Future[Stats] = {
    val shouldMatch = Stats("").list
    val updatedUserStats = stats.copy(list = shouldMatch.map { ls =>
      ls._1 -> stats.list
        .getOrElse(ls._1, ls._2)
        .copy(
          name = ls._2.name
        )
    })
    statsDAO.replace(updatedUserStats)
  }

  def updateLastCacheId(tokenId: String, sessionId: String) =
    playerAccountActor ! UpdateCacheId(tokenId, sessionId)

  /*
   * Assembles galaxy data back into its nested data structure
   * */
  def getGalaxyData(tokenId: String, path: Option[String]): Future[GalaxyData] = {
    for {
      stats <- getStats(tokenId)
    } yield GalaxyData(stats, path.getOrElse("00000"))
  }

  /*
   * Assembles star system data back into its nested data structure
   * (joe) TODO USE
   * */
  def getStarSystemData(tokenId: String, path: Option[String]): Future[StarSystemData] = {
    for {
      stats <- getStats(tokenId)
    } yield StarSystemData(stats, path.getOrElse("00000"))
  }

  def getPath(tokenId: String): Future[String] = {
    for {
      stats <- getStats(tokenId)
    } yield stats.currentPath
  }

  /*
   * Creates built continent ready for the client to render
   * also includes functions for that continent
   * */
  private def createBuiltContinent(tokenId: String,
                                   path: String,
                                   isSandbox: Boolean = false): Future[BuiltContinent] = {
    getFunctions(tokenId).map { functions =>
      if (isSandbox) {
        BuiltContinent(functions, sandbox, Some(functionsDAO))
      } else {
        val continent = getContinentData(path)
        val collectedPreBuilts = gatherAssignedStagedAndPrebuiltActive(path)
        statsDAO.updateCurrentLevel(tokenId, path)
        BuiltContinent(
          functions,
          continent.copy(
            continentStruct = continent.continentStruct.map(
              cs => cs.copy(preBuiltActive = collectedPreBuilts._1, assignedStaged = collectedPreBuilts._2)
            )
          ),
          Some(functionsDAO)
        )
      }
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

  def clientInit(tokenId: String): Future[(String, GalaxyData, PathAndContinent)] = {
    for {
      stats <- getStats(tokenId)
      builtContinent <- createBuiltContinent(tokenId, stats.currentPath, stats.isSandbox.getOrElse(false))
    } yield
      (stats.currentPath, GalaxyData(stats, stats.currentPath), PathAndContinent(stats.currentPath, builtContinent))
  }

  def getStarSystemData(p: String): CelestialSystem = {
    val path = Stats.makePath(p)
    superCluster
      .children(path(1))
      .children(path(2))
  }

  // (joe) todo use
  def getPlanetData(p: String): CelestialSystem = {
    val path = Stats.makePath(p)
    superCluster
      .children(path(1))
      .children(path(2))
      .children(path(3))
  }

  // (joe) todo use
  def getContinentData(p: String): CelestialSystem = {
    val path = Stats.makePath(p)
    superCluster
      .children(path(1))
      .children(path(2))
      .children(path(3))
      .children(path.drop(4).mkString("").toInt)
  }

  /*
   * Gets list of video ids for specified path
   * */
  def getVideoIds(path: String): List[String] = {
    getContinentData(path).continentStruct.map(_.videoHints).getOrElse(List.empty[String])
  }

  private def calibrateColors(functions: Functions) = {
    def updateFunc(func: List[Function]): List[Function] = {
      func.map {
        case f if !listedElements.exists(_.name == f.color) => f.copy(color = white.name, func = f.func.map(updateFunc))
        case f => f.copy(func = f.func.map(updateFunc))
      }
    }
    functions.copy(list = updateFunc(functions.list.values.toList).map(f => f.created_id -> f).toMap)
  }

  /*
   * Gets all functions for user
   * */
  private def getFunctions(tokenId: String): Future[Functions] = {
    for {
      playerToken <- playerTokenDAO.getToken(tokenId)
      functions <- functionsDAO.find(tokenId)
    } yield
      calibrateColors(
        functions match {
          case Some(f) => f
          case None =>
            playerToken match {
              case Some(PlayerToken(_, lambdas, _, _, _)) if lambdas.isDefined => // legacy account
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
      )
  }

  /*
   * Updates function at the top level, only good when adding function to func property
   * this is the most efficient way to update a function.
   * Not good for updating color, name, or displayImage. Use updateFunctionProperties instead.
   * */
  def updateFunction(tokenId: String, function: Function): Future[Function] =
    for {
      _ <- functionsDAO.updateFunction(tokenId, function)
    } yield function

  /*
   * Updates all instances of passed in functions then returns updated PathAndContinent
   * for client to update all instances of function
   * */
  def updateFunctionProperties(tokenId: String, function: Function): Future[PathAndContinent] = {
    for {
      functions <- getFunctions(tokenId)
      _ <- functionsDAO.replaceAll(
        tokenId,
        Functions(
          tokenId,
          changedAllInstances(functions.listed, function)
        )
      )
      pathAndContinent <- resetContinent(tokenId)
    } yield pathAndContinent
  }

  /*
   * @deprecated
   * */
  def changeFunctionColor(tokenId: String, function: Function): Future[PathAndContinent] = {
    for {
      funcOpt <- functionsDAO.findFunction(tokenId, function.created_id)
      pathAndContinent <- funcOpt match {
        case Some(func) =>
          val color = nextColor(func.color)
          updateFunctionProperties(
            tokenId,
            func.copy(
              color = color
            )
          )
        case None => resetContinent(tokenId)
      }
    } yield pathAndContinent
  }

  /*
   * Recursively updates all instances of a functions color, name, displayName
   * */
  private def changedAllInstances(functions: List[Function], function: Function): List[Function] = functions.map { ft =>
    (if (ft.created_id == function.created_id) function
     else ft).copy(func = ft.func.map(changedAllInstances(_, function)))
  }

  /*
   * Used to activate or deactivate a function (move from staged to active or vis versa)
   * Passed in function index should be new index in list
   * Passed in function category should be new list (staged or active)
   * */
  def activateDeactivateFunction(tokenId: String, function: Function): Future[PreparedFunctions] = {
    for {
      stats <- getStats(tokenId)
      functions <- getFunctions(tokenId)
      path <- getPath(tokenId)
    } yield
      PreparedFunctions(functions,
                        if (stats.isSandbox.getOrElse(false)) sandbox.continentStruct.get
                        else getContinentData(path).continentStruct.get,
                        function,
                        functionsDAO)
  }

  /*
   * Gets a users stats
   * */
  def getStats(tokenId: String): Future[Stats] = {
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
      }).flatMap {
      _.flatMap { stats =>
        calibrateContinentPath(stats, stats.currentPath).map(path => stats.copy(currentPath = path))
      }
    }
  }

  private def calibrateContinentPath(stats: Stats, path: String): Future[String] = {
    if (path.length > 4 && stats.list.contains(path)) {
      FastFuture.successful(path)
    } else {
      val defaultPath = "00000"
      for {
        _ <- updatePath(stats.tokenId, defaultPath)
      } yield defaultPath
    }
  }

  /*
   * Gets the continent data and current path
   * If path is omitted the current path in the db will be used
   * This is the main function used by the outside to get a built continent
   * */
  def getBuiltContinent(tokenId: String, pathOpt: Option[String] = None): Future[PathAndContinent] = {
    for {
      _ <- statsDAO.setIsSandbox(tokenId, bool = false)
      stats <- getStats(tokenId)
      calibratedPath <- calibrateContinentPath(stats, pathOpt.getOrElse(stats.currentPath))
      continent <- createBuiltContinent(tokenId, calibratedPath)
    } yield PathAndContinent(pathOpt.getOrElse(stats.currentPath), continent)
  }

  def resetContinent(tokenId: String, pathOpt: Option[String] = None): Future[PathAndContinent] = {
    for {
      stats <- getStats(tokenId)
      calibratedPath <- calibrateContinentPath(stats, pathOpt.getOrElse(stats.currentPath))
      continent <- createBuiltContinent(tokenId, calibratedPath, stats.isSandbox.getOrElse(false))
    } yield PathAndContinent(calibratedPath, continent)
  }

  def compilerBuiltContinent(tokenId: String): Future[BuiltContinent] = {
    for {
      stats <- getStats(tokenId)
      continent <- createBuiltContinent(tokenId, stats.currentPath, stats.isSandbox.getOrElse(false))
    } yield continent
  }

  /*
   * Advances the user stats to the next level if success is true otherwise
   * updates times played and last played.
   * */
  def advanceStats(tokenId: String, success: Boolean): Future[PathAndContinent] = {
    for {
      stats <- getStats(tokenId)
      newPath <- stats.isSandbox match {
        case Some(t) if t => FastFuture.successful(stats.currentPath)
        case _ =>
          statsDAO.incrementWinsAndTimedPlayed(tokenId, success)
      }
      continent <- createBuiltContinent(tokenId, newPath, stats.isSandbox.getOrElse(false))
    } yield PathAndContinent(newPath, continent)
  }

  /*
   * Used to update path in db
   * */
  def updatePath(tokenId: String, path: String): Future[String] =
    for {
      _ <- statsDAO.updatePath(tokenId, path)
    } yield path

  /*
   * Used to unlock all levels
   * In the future this should be part of the admin screen
   * */
  def unlock(tokenId: String): Future[Stats] = {
    for {
      _ <- getStats(tokenId)
      unlocked <- statsDAO.unlock(tokenId)
    } yield unlocked
  }

  def getSandbox(tokenId: String): Future[PathAndContinent] = {
    for {
      _ <- statsDAO.setIsSandbox(tokenId, bool = true)
      path <- getPath(tokenId)
      continent <- createBuiltContinent(tokenId, "", isSandbox = true)
    } yield PathAndContinent(path, continent)
  }
}
