package actors.messages.level

import level_gen.SuperClusters
import level_gen.models.CelestialSystem
import play.api.libs.json.{Json, OFormat}
import types._

import scala.collection.mutable

object Stats {
  implicit val format: OFormat[Stats] = Json.format[Stats]

  val superCluster: CelestialSystem = SuperClusters.getCluster("SuperCluster1")
  final val tokenIdLabel: TokenId = "tokenId"
  final val superClusterLabel: String = "superClusterInd"
  final val galaxyLabel: String = "galaxyInd"
  final val starSystemLabel: String = "starSystemInd"
  final val planetLabel: String = "planetInd"
  final val continentLabel: String = "continentInd"
  final val superClusterStatsLabel: String = "superClusterStats"
  final val galaxyStatsLabel: String = "galaxyStats"
  final val starSystemStatsLabel: String = "starSystemStats"
  final val planetStatsLabel: String = "planetStats"
  final val continentStatsLabel: String = "continentStatsLabel"

  def makePath(str: String): Array[Int] = str.split("").map(_.toInt)

  // New stats
  def apply(tId: TokenId): Stats = {
    var listStats: mutable.Map[String, LayerStatistic] =
      mutable.Map("0" -> LayerStatistic(name = superCluster.name, active = true))

    superCluster.children.zipWithIndex.map { g =>
      listStats += (s"0${g._2}" -> LayerStatistic(
        name = g._1.name,
        active = g._2 == 0
      ))
      g._1.children.zipWithIndex.map { s =>
        listStats += (s"0${g._2}${s._2}" -> LayerStatistic(
          name = s._1.name,
          active = g._2 == 0 && s._2 == 0
        ))
        s._1.children.zipWithIndex.map { p =>
          listStats += (s"0${g._2}${s._2}${p._2}" -> LayerStatistic(
            name = p._1.name,
            active = g._2 == 0 && s._2 == 0 && p._2 == 0
          ))
          p._1.children.zipWithIndex.map { c =>
            listStats += (s"0${g._2}${s._2}${p._2}${c._2}" -> LayerStatistic(
              name = p._1.name,
              active = g._2 == 0 && s._2 == 0 && p._2 == 0 && c._2 == 0
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

  // Converts legacy stats to new stats
  def apply(tokenId: TokenId, originalStats: models.CurrentStats): Stats = {
    val superCluster: CelestialSystem = SuperClusters.getCluster("SuperCluster1")

    val listStats: mutable.Map[String, LayerStatistic] =
      mutable.Map("0" -> LayerStatistic("SuperCluster1", active = true))

    superCluster.children.zipWithIndex.map { g =>
      listStats += (s"0${g._2}" -> LayerStatistic(
        name = g._1.name,
        active = g._2 == 0
      ))
      g._1.children.zipWithIndex.map { s =>
        listStats += (s"0${g._2}${s._2}" -> LayerStatistic(
          name = s._1.name,
          active = g._2 == 0 && s._2 == 0
        ))
        s._1.children.zipWithIndex.map { p =>
          listStats += (s"0${g._2}${s._2}${p._2}" -> LayerStatistic(
            name = p._1.name,
            active = originalStats.levels
              .get(p._1.name)
              .flatMap(p => p.find(_._2.prevStep == "None").map(_._2.active))
              .getOrElse(false)
          ))
          p._1.children.zipWithIndex.map { c =>
            listStats += (s"0${g._2}${s._2}${p._2}${c._2}" -> LayerStatistic(
              name = c._1.name,
              active = originalStats.levels.get(p._1.name).flatMap(_.get(c._1.name).map(_.active)).getOrElse(false),
              timesPlayed =
                originalStats.levels.get(p._1.name).flatMap(_.get(c._1.name).map(_.timesPlayed)).getOrElse(0),
              wins = originalStats.levels
                .get(p._1.name)
                .flatMap(_.get(c._1.name).map(_.wins.getOrElse(0)))
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
