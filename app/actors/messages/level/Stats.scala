package actors.messages.level

import level_gen.SuperClusters
import level_gen.models.CelestialSystem
import models.deprecatedPlayerToken.CurrentStats
import play.api.libs.json.{Json, OFormat}
import types._

import scala.collection.mutable

object Stats {
  implicit val format: OFormat[Stats] = Json.format[Stats]

  def superCluster: CelestialSystem = SuperClusters.getCluster("SuperCluster1")

  final val tokenIdLabel: TokenId = "tokenId"
  final val currentPathLabel: String = "currentPath"
  final val listLabel: String = "list"

  def makePath(str: String): Array[Int] = str.split("").map(_.toInt)

  private def isFreePlay(starSystem: CelestialSystem): Option[Boolean] = {
    starSystem.starSystemStruct.map(_.freePlay)
  }

  // New stats
  def apply(tId: TokenId): Stats = {
    val listStats: mutable.Map[String, LayerStatistic] =
      mutable.Map("0" -> LayerStatistic("SuperCluster1", active = true))

    superCluster.children.zipWithIndex.map { galaxy =>
      listStats += (s"0${galaxy._2}" -> LayerStatistic(
        name = galaxy._1.name,
        active = galaxy._2 == 0
      ))
      galaxy._1.children.zipWithIndex.map { starSystem =>
        listStats += (s"0${galaxy._2}${starSystem._2}" -> LayerStatistic(
          name = starSystem._1.name,
          active = isFreePlay(starSystem._1)
            .getOrElse(galaxy._2 == 0 && starSystem._2 == 0)
        ))
        starSystem._1.children.zipWithIndex.map { planet =>
          listStats += (s"0${galaxy._2}${starSystem._2}${planet._2}" -> LayerStatistic(
            name = planet._1.name,
            active = isFreePlay(starSystem._1)
              .getOrElse(galaxy._2 == 0 && starSystem._2 == 0 && planet._2 == 0)
          ))
          planet._1.children.zipWithIndex.map { continent =>
            listStats += (s"0${galaxy._2}${starSystem._2}${planet._2}${continent._2}" -> LayerStatistic(
              name = planet._1.name,
              active = isFreePlay(starSystem._1)
                .getOrElse(galaxy._2 == 0 && starSystem._2 == 0 && planet._2 == 0 && continent._2 == 0)
            ))
          }
        }
      }
    }

    new Stats(
      tokenId = tId,
      currentPath = "00000",
      list = listStats.toMap
    )
  }

  private def checkLastLevelBasicProgramming(originalStats: CurrentStats): Boolean = {
    originalStats.levels
      .get("Conditionals")
      .flatMap { c =>
        c.find(_._2.nextStep == "None").flatMap(_._2.wins.map(_ > 0))
      }
      .getOrElse(false)
  }

  // Converts legacy stats to new stats
  def apply(tokenId: TokenId, originalStats: CurrentStats): Stats = {
    val listStats: mutable.Map[String, LayerStatistic] =
      mutable.Map("0" -> LayerStatistic("SuperCluster1", active = true))

    superCluster.children.zipWithIndex.map { galaxy =>
      listStats += (s"0${galaxy._2}" -> LayerStatistic(
        name = galaxy._1.name,
        active = galaxy._2 == 0
      ))
      galaxy._1.children.zipWithIndex.map { starSystem =>
        listStats += (s"0${galaxy._2}${starSystem._2}" -> LayerStatistic(
          name = starSystem._1.name,
          active = {
            // checks if last level of basic programming has been beat to unlock next star system
            isFreePlay(starSystem._1).getOrElse {
              if (starSystem._2 == 1) {
                checkLastLevelBasicProgramming(originalStats)
              } else {
                galaxy._2 == 0 && starSystem._2 == 0
              }
            }
          }
        ))
        starSystem._1.children.zipWithIndex.map { planet =>
          listStats += (s"0${galaxy._2}${starSystem._2}${planet._2}" -> LayerStatistic(
            name = planet._1.name,
            active = {
              // checks if last level of basic programming has been beat to unlock next star system
              isFreePlay(starSystem._1).getOrElse {
                if (starSystem._2 == 1 && planet._2 == 0) {
                  checkLastLevelBasicProgramming(originalStats)
                } else {
                  originalStats.levels
                    .get(planet._1.name)
                    .flatMap(p => p.find(_._2.prevStep == "None").map(_._2.active))
                    .getOrElse(false)
                }
              }
            }
          ))
          planet._1.children.zipWithIndex.map { continent =>
            listStats += (s"0${galaxy._2}${starSystem._2}${planet._2}${continent._2}" -> LayerStatistic(
              name = continent._1.name,
              active = {
                // checks if last level of basic programming has been beat to unlock next star system
                isFreePlay(starSystem._1).getOrElse {
                  if (starSystem._2 == 1 && continent._2 == 0) {
                    checkLastLevelBasicProgramming(originalStats)
                  } else {
                    originalStats.levels
                      .get(planet._1.name)
                      .flatMap(_.get(continent._1.name).map(_.active))
                      .getOrElse(false)
                  }
                }
              },
              timesPlayed = originalStats.levels
                .get(planet._1.name)
                .flatMap(_.get(continent._1.name).map(_.timesPlayed))
                .getOrElse(0),
              wins = originalStats.levels
                .get(planet._1.name)
                .flatMap(_.get(continent._1.name).map(_.wins.getOrElse(0)))
                .getOrElse(0)
            ))
          }
        }
      }
    }

    new Stats(
      tokenId = tokenId,
      currentPath = "00000",
      list = listStats.toMap
    )
  }
}

case class Stats(
    tokenId: TokenId,
    currentPath: String,
    list: Map[String, LayerStatistic]
) {
  def superClusterPath: String = this.currentPath.take(1)
  def galaxyPath: String = this.currentPath.take(2)
  def starSystemPath: String = this.currentPath.take(3)
  def planetPath: String = this.currentPath.take(4)
  def continentPath: String = this.currentPath
}
