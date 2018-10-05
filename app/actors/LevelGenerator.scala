package actors

import actors.messages.{AssignedFunction, RawLevelData, RawStepData}
import play.api.Environment
import play.api.libs.json.Json
import types.{LevelName, StepName}

import scala.io.Source

class LevelGenerator(environment: Environment) {

  private val dirName = "assets"

  def getListOfLevels: List[String] = {
    environment.getExistingFile(dirName).map { dir =>
      dir.listFiles.filter(_.isFile).map(_.getAbsolutePath).toList
    } orElse {
      environment.getExistingFile(s"conf/$dirName").map { dir =>
        dir.listFiles.filter(_.isFile).map(_.getAbsolutePath).toList
      }
    }
  }.getOrElse(List.empty[String])

  def getAllLevels: Map[String, RawLevelData] = {
    val levels = getListOfLevels
      .map { level =>
        getJsonFromFile(level) match {
          case Some(rawLevelData) =>
            if (rawLevelData.show) {
              (rawLevelData.level, Some(rawLevelData))
            } else ("nothing", None)
          case None => ("nothing", None)
        }
      }
      .toMap
      .filterNot(rld => rld._2.isEmpty)
      .map(rld => (rld._1, rld._2.get))

    levels.map { rld =>
      levels.get(rld._2.nextLevel) match {
        case Some(_) => rld
        case None => rld._1 -> rld._2.copy(nextLevel = "None")
      }
    }
  }

  def getJsonFromFile(filePath: String): Option[RawLevelData] =
    Json.parse(Source.fromFile(filePath).getLines().mkString("")).validate[RawLevelData].asOpt

  final val preBuiltTag: Symbol = 'preBuiltActives
  final val assignedTag: Symbol = 'assignedStaged

  lazy val levelsAndStepsToList: List[(LevelName, List[RawStepData])] = {
    listLevels() map { rld =>
      rld.level -> listSteps(rld.steps)
    }
  }

  private def listSteps(steps: Map[String, RawStepData],
                        listRsd: List[RawStepData] = List.empty[RawStepData]): List[RawStepData] = listRsd match {
    case Nil =>
      listSteps(steps, List(steps.find(_._2.prevStep == "None").get._2))
    case newList if newList.last.nextStep == "None" => listRsd
    case newList => listSteps(steps, listRsd ++ List(steps(newList.last.nextStep)))
  }

  private def listLevels(levels: Map[String, RawLevelData] = getAllLevels,
                         listRld: List[RawLevelData] = List.empty[RawLevelData]): List[RawLevelData] = listRld match {
    case Nil =>
      listLevels(levels, List(levels.find(_._2.prevLevel == "None").get._2))
    case newList if newList.last.nextLevel == "None" => listRld
    case newList => listLevels(levels, listRld ++ List(levels(newList.last.nextLevel)))
  }

  def getAllowedStaged(level: LevelName, step: StepName): List[AssignedFunction] = {
    levelsAndStepsToList.reverse.dropWhile(_._1 != level).reverse.flatMap { p =>
      if (p._1 == level) {
        p._2.reverse
          .dropWhile(rsd => rsd.step != step)
          .reverse
          .flatMap(rsd => rsd.assignedStaged.map(_.copy(levelAndStep = Some(rsd.level -> rsd.step))))
      } else {
        p._2.flatMap(rsd => rsd.assignedStaged.map(_.copy(levelAndStep = Some(rsd.level -> rsd.step))))
      }
    }
  }

  def getAllowedActives(level: LevelName, step: StepName): List[AssignedFunction] = {
    levelsAndStepsToList.reverse.dropWhile(_._1 != level).reverse.flatMap { p =>
      if (p._1 == level) {
        p._2.reverse
          .dropWhile(rsd => rsd.step != step)
          .reverse
          .flatMap(rsd => rsd.preBuiltActive.map(_.copy(levelAndStep = Some(rsd.level -> rsd.step))))
      } else {
        p._2.flatMap(rsd => rsd.preBuiltActive.map(_.copy(levelAndStep = Some(rsd.level -> rsd.step))))
      }
    }
  }

  def getRawStepData(level: LevelName, step: LevelName): Option[RawStepData] =
    getAllLevels.get(level) match {
      case Some(levelData) =>
        levelData.steps.get(step)
      case None => None
    }

  def getAssignedFunctions: List[AssignedFunction] = {
    getAllLevels.values
      .flatMap(_.steps.values)
      .flatMap(rsd => rsd.assignedStaged ++ rsd.preBuiltActive)
      .toList
  }

  def verifyLevelAndStepName(levelName: LevelName, stepName: StepName): (LevelName, StepName) = {
    getRawStepData(levelName, stepName) match {
      case Some(_) => levelName -> stepName
      case None =>
        val allLevels = getAllLevels
        allLevels.get(levelName) match {
          case Some(rawLevelData) =>
            levelName -> rawLevelData.steps.filter(_._2.prevStep == "None").head._1
          case None =>
            "BasicProgramming" -> allLevels("BasicProgramming").steps.filter(_._2.prevStep == "None").head._1
        }
    }
  }
}
