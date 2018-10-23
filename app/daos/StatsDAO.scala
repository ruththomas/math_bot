package daos

import java.util.Date

import com.google.inject.Inject
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.{Completed, MongoCollection, MongoDatabase, Observable}
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import actors.messages.level.{LayerStatistic, Stats}
import level_gen.models._
import types.TokenId
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.result.UpdateResult

import scala.concurrent.{ExecutionContext, Future}

class StatsDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {
  final val collectionLabel = "stats"
  import Stats._
  import LayerStatistic._

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[Stats](),
      Macros.createCodecProvider[Stats](),
      Macros.createCodecProvider[CelestialSystem](),
      Macros.createCodecProvider[LayerStatistic](),
      Macros.createCodecProvider[ContinentStruct]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[Stats] =
    mathbotDb.getCollection[Stats](collectionLabel).withCodecRegistry(codecRegistry)

  def insert(stats: Stats): Future[Option[Completed]] =
    collection.insertOne(stats).toFutureOption()

  def findStats(tokenId: TokenId): Future[Option[Stats]] =
    collection.find(equal(tokenIdLabel, tokenId)).first().toFutureOption()

  def gatherGalaxy(tokenId: TokenId, path: String): Future[Option[Map[TokenId, Map[String, LayerStatistic]]]] = {
    val key = path.take(2)
    collection
      .find(equal(tokenIdLabel, tokenId))
      .first()
      .toFutureOption()
      .map {
        _.map { stats =>
          Map(
            "galaxy" -> Map(key -> stats.list(key)),
            "starSystems" -> stats.list.filterKeys(l => l.length == 3 && l.take(key.length).contains(key))
          )
        }
      }
  }

  def gatherStarSystem(tokenId: TokenId, path: String): Future[Option[Map[TokenId, Map[String, LayerStatistic]]]] = {
    val key = path.take(3)
    collection.find(equal(tokenIdLabel, tokenId)).first().toFutureOption().map {
      _.map { stats =>
        Map(
          "starSystem" -> Map(key -> stats.list(key)),
          "planets" -> stats.list.filterKeys(l => l.length == 4 && l.take(key.length).contains(key)),
          "continents" -> stats.list.filterKeys(l => l.length > 4 && l.take(key.length).contains(key))
        )
      }
    }
  }

  def updateCurrentLevel(tokenId: TokenId, path: String): Future[Option[UpdateResult]] = {
    collection
      .updateOne(
        equal(tokenIdLabel, tokenId),
        combine(
          set(currentPathLabel, path)
        )
      )
      .toFutureOption()
  }

  private def incContinent(path: String): TokenId = path.init + (path.last.asDigit + 1).toString
  private def incPlanet(path: String): TokenId = path.take(3) + (path(3).asDigit + 1).toString + path.last
  private def incStarSystem(path: String): TokenId = path.take(2) + (path(2).asDigit + 1).toString + path.drop(3)
  private def incGalaxy(path: String): TokenId = path.head + (path(1).asDigit + 1).toString + path.drop(4)
  private def incSuperCluster(path: String): TokenId = (path.head.asDigit + 1).toString + path.tail

  private def computeNewPath(stats: Stats,
                             position: Int = 4)(path: String = incContinent(stats.currentPath)): String = {
    stats.list.get(path) match {
      case Some(_) => path
      case None =>
        val zeroLast = path.take(position) + path.drop(position + 1).split("").map(_ => 0).mkString("")
        if (position == 4) incPlanet(zeroLast)
        else if (position == 3) computeNewPath(stats, position - 1)(incStarSystem(zeroLast))
        else if (position == 2) computeNewPath(stats, position - 1)(incGalaxy(zeroLast))
        else if (position == 1) computeNewPath(stats, position - 1)(incSuperCluster(zeroLast))
        else {
          // beat the game
          stats.currentPath
        }
    }
  }

  def incrementWinsAndTimedPlayed(tokenId: TokenId, incrementWins: Boolean): Future[Stats] = {
    val newDate = new Date()
    (for {
      stats <- collection.find(equal(tokenIdLabel, tokenId))
      currentPath = stats.currentPath
      updated <- collection.findOneAndUpdate(
        equal(tokenIdLabel, tokenId),
        combine(
          set("currentPath", if (incrementWins) computeNewPath(stats)() else currentPath),
          inc(s"$listLabel.$currentPath.$timesPlayedLabel", 1),
          inc(s"$listLabel.$currentPath.$winsLabel", if (incrementWins) 1 else 0),
          set(s"list.$currentPath.$lastPlayedLabel", newDate),
          inc(s"$listLabel.${currentPath.take(4)}.$timesPlayedLabel", 1),
          inc(s"$listLabel.${currentPath.take(4)}.$winsLabel", if (incrementWins) 1 else 0),
          set(s"list.${currentPath.take(4)}.$lastPlayedLabel", newDate),
          inc(s"$listLabel.${currentPath.take(3)}.$timesPlayedLabel", 1),
          inc(s"$listLabel.${currentPath.take(3)}.$winsLabel", if (incrementWins) 1 else 0),
          set(s"list.${currentPath.take(3)}.$lastPlayedLabel", newDate),
          inc(s"$listLabel.${currentPath.take(2)}.$timesPlayedLabel", 1),
          inc(s"$listLabel.${currentPath.take(2)}.$winsLabel", if (incrementWins) 1 else 0),
          set(s"list.${currentPath.take(2)}.$lastPlayedLabel", newDate),
          inc(s"$listLabel.${currentPath.take(1)}.$timesPlayedLabel", 1),
          inc(s"$listLabel.${currentPath.take(1)}.$winsLabel", if (incrementWins) 1 else 0),
          set(s"list.${currentPath.take(1)}.$lastPlayedLabel", newDate)
        )
      )
    } yield updated).toFuture().map(_.head)
  }
}
