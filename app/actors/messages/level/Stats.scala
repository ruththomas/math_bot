package actors.messages.level

import level_gen.SuperClusters
import level_gen.models.SuperCluster
import play.api.libs.json.{Json, OFormat}
import types._

import scala.collection.mutable

object Stats {
  implicit val format: OFormat[Stats] = Json.format[Stats]
  // New stats
  def apply(tId: TokenId): Stats = {
    val superCluster: SuperCluster = SuperClusters.getCluster("SuperCluster1")

    val superClustersStats: mutable.Map[Int, LayerStatistic] =
      mutable.Map(0 -> LayerStatistic("SuperCluster1", active = true))
    var galaxiesStats: mutable.Map[(Int, Int), LayerStatistic] = mutable.Map.empty[(Int, Int), LayerStatistic]
    var starSystemsStats: mutable.Map[(Int, Int, Int), LayerStatistic] =
      mutable.Map.empty[(Int, Int, Int), LayerStatistic]
    var planetsStats: mutable.Map[(Int, Int, Int, Int), LayerStatistic] =
      mutable.Map.empty[(Int, Int, Int, Int), LayerStatistic]
    var continentsStats: mutable.Map[(Int, Int, Int, Int, Int), LayerStatistic] =
      mutable.Map.empty[(Int, Int, Int, Int, Int), LayerStatistic]

    superCluster.galaxies.zipWithIndex.map { g =>
      galaxiesStats += ((0, g._2) -> LayerStatistic(
        name = g._1.name,
        active = g._2 == 0
      ))
      g._1.starSystems.zipWithIndex.map { s =>
        starSystemsStats += ((0, g._2, s._2) -> LayerStatistic(
          name = s._1.name,
          active = g._2 == 0 && s._2 == 0
        ))
        s._1.planets.zipWithIndex.map { p =>
          planetsStats += ((0, g._2, s._2, p._2) -> LayerStatistic(
            name = p._1.name,
            active = g._2 == 0 && s._2 == 0 && p._2 == 0
          ))
          p._1.continents.zipWithIndex.map { c =>
            continentsStats += ((0, g._2, s._2, p._2, c._2) -> LayerStatistic(
              name = p._1.name,
              active = g._2 == 0 && s._2 == 0 && p._2 == 0 && c._2 == 0
            ))
          }
        }
      }
    }

    new Stats(
      tokenId = tId,
      superClusterInd = 0,
      galaxyInd = 0,
      starSystemInd = 0,
      planetInd = 0,
      continentInd = 0,
      superClusterStats = superClustersStats.toMap.map(m => m._1.toString -> m._2),
      galaxyStats = galaxiesStats.toMap.map(m => m._1.toString -> m._2),
      starSystemStats = starSystemsStats.toMap.map(m => m._1.toString -> m._2),
      planetStats = planetsStats.toMap.map(m => m._1.toString -> m._2),
      continentStats = continentsStats.toMap.map(m => m._1.toString -> m._2)
    )
  }

  // Converts legacy stats to new stats
  def apply(tokenId: TokenId, originalStats: models.Stats): Stats = {
    val superCluster: SuperCluster = SuperClusters.getCluster("SuperCluster1")

    val superClustersStats: mutable.Map[Int, LayerStatistic] =
      mutable.Map(0 -> LayerStatistic("SuperCluster1", active = true))
    var galaxiesStats: mutable.Map[(Int, Int), LayerStatistic] = mutable.Map.empty[(Int, Int), LayerStatistic]
    var starSystemsStats: mutable.Map[(Int, Int, Int), LayerStatistic] =
      mutable.Map.empty[(Int, Int, Int), LayerStatistic]
    var planetsStats: mutable.Map[(Int, Int, Int, Int), LayerStatistic] =
      mutable.Map.empty[(Int, Int, Int, Int), LayerStatistic]
    var continentsStats: mutable.Map[(Int, Int, Int, Int, Int), LayerStatistic] =
      mutable.Map.empty[(Int, Int, Int, Int, Int), LayerStatistic]

    superCluster.galaxies.zipWithIndex.map { g =>
      galaxiesStats += ((0, g._2) -> LayerStatistic(
        name = g._1.name,
        active = g._2 == 0
      ))
      g._1.starSystems.zipWithIndex.map { s =>
        starSystemsStats += ((0, g._2, s._2) -> LayerStatistic(
          name = s._1.name,
          active = g._2 == 0 && s._2 == 0
        ))
        s._1.planets.zipWithIndex.map { p =>
          planetsStats += ((0, g._2, s._2, p._2) -> LayerStatistic(
            name = p._1.name,
            active = originalStats.levels
              .get(p._1.name)
              .flatMap(p => p.find(_._2.prevStep == "None").map(_._2.active))
              .getOrElse(false)
          ))
          p._1.continents.zipWithIndex.map { c =>
            continentsStats += ((0, g._2, s._2, p._2, c._2) -> LayerStatistic(
              name = c._1.name,
              active = originalStats.levels.get(p._1.name).flatMap(_.get(c._1.name).map(_.active)).getOrElse(false),
              timesPlayed =
                originalStats.levels.get(p._1.name).flatMap(_.get(c._1.name).map(_.timesPlayed)).getOrElse(0),
              wins = originalStats.levels.get(p._1.name).flatMap(_.get(c._1.name).map(_.wins.getOrElse(0))).getOrElse(0)
            ))
          }
        }
      }
    }

    new Stats(
      tokenId = tokenId,
      superClusterInd = 0,
      galaxyInd = 0,
      starSystemInd = 0,
      planetInd = 0,
      continentInd = 0,
      superClusterStats = superClustersStats.toMap.map(m => m._1.toString -> m._2),
      galaxyStats = galaxiesStats.toMap.map(m => m._1.toString -> m._2),
      starSystemStats = starSystemsStats.toMap.map(m => m._1.toString -> m._2),
      planetStats = planetsStats.toMap.map(m => m._1.toString -> m._2),
      continentStats = continentsStats.toMap.map(m => m._1.toString -> m._2)
    )
  }
}

case class Stats(
    tokenId: TokenId,
    superClusterInd: Int = 0,
    galaxyInd: Int = 0,
    starSystemInd: Int = 0,
    planetInd: Int = 0,
    continentInd: Int = 0,
    superClusterStats: Map[String, LayerStatistic],
    galaxyStats: Map[String, LayerStatistic],
    starSystemStats: Map[String, LayerStatistic],
    planetStats: Map[String, LayerStatistic],
    continentStats: Map[String, LayerStatistic]
)
