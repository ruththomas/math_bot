package daos

import java.util.Date

import actors.messages.admin.{CurrentPath, LevelStats}
import actors.messages.level.{LayerStatistic, Stats}
import actors.messages.playeraccount.MaxLevel
import com.google.inject.Inject
import level_gen.models._
import loggers.SemanticLog
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Indexes.ascending
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.result.UpdateResult
import org.mongodb.scala.{Completed, MongoCollection, MongoDatabase}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class StatsDAO @Inject()(mathbotDb: MongoDatabase, aLogger: SemanticLog)(implicit ec: ExecutionContext) {
  final val collectionLabel = "user_stats"
  import LayerStatistic._
  import Stats._

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[Stats](),
      Macros.createCodecProvider[CelestialSystem](),
      Macros.createCodecProvider[LayerStatistic](),
      Macros.createCodecProvider[ContinentStruct]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[Stats] =
    mathbotDb.getCollection[Stats](collectionLabel).withCodecRegistry(codecRegistry)

  val currentPathCollection: MongoCollection[CurrentPath] =
    mathbotDb
      .getCollection[CurrentPath](collectionLabel)
      .withCodecRegistry(
        fromRegistries(
          fromProviders(
            Macros.createCodecProvider[CurrentPath]()
          ),
          DEFAULT_CODEC_REGISTRY
        )
      )

  val levelStatsCollection: MongoCollection[LevelStats] =
    mathbotDb
      .getCollection[LevelStats](collectionLabel)
      .withCodecRegistry(
        fromRegistries(
          fromProviders(
            Macros.createCodecProvider[LevelStats]()
          ),
          DEFAULT_CODEC_REGISTRY
        )
      )

  val maxLevelCollection: MongoCollection[MaxLevel] =
    mathbotDb
      .getCollection[MaxLevel](collectionLabel)
      .withCodecRegistry(
        fromRegistries(
          fromProviders(
            Macros.createCodecProvider[MaxLevel]()
          ),
          DEFAULT_CODEC_REGISTRY
        )
      )

  def insert(stats: Stats): Future[Option[Completed]] =
    collection.insertOne(stats).toFutureOption()

  def replace(stats: Stats): Future[Stats] =
    collection.replaceOne(equal(tokenIdLabel, stats.tokenId), stats).toFutureOption().map(_ => stats)

  def findStats(tokenId: String): Future[Option[Stats]] = {
    collection
      .find(equal(tokenIdLabel, tokenId))
      .first()
      .toFutureOption()
      .map(_.map {
        case s if s.maxContinent.nonEmpty => s
        case s => s.copy(maxContinent = Some(updateMaxContinent(s)))
      })
  }

  def updateCurrentLevel(tokenId: String, path: String): Future[Option[UpdateResult]] = {
    collection
      .updateOne(
        equal(tokenIdLabel, tokenId),
        combine(
          set(currentPathLabel, path)
        )
      )
      .toFutureOption()
  }

  private def updateMaxContinent(stats: Stats, newPath: String = "00000"): String = {
    val orderedContinents = stats.continentsInOrder
    val lastActive = orderedContinents.lastIndexWhere(_.stats.active)
    val indexOfPath = orderedContinents.lastIndexWhere(_.id == newPath)

    (lastActive, indexOfPath) match {
      case (a, p) if a < 0 && p < 0 => "00000" // Something is wrong, set to zero
      case (a, _) if a < 0 => "00000" // continents did not contain an active=true field
      case (_, p) if p < 0 => orderedContinents(lastActive).id // continents did not contain newPath
      case (a, p) if a <= p => newPath // newPath is after or is at furthest active
      case (a, p) if a > p => orderedContinents(a).id // newPath is before lastActive
    }
  }

  private def incContinent(path: String): String = path.init + (path.last.asDigit + 1).toString
  private def incPlanet(path: String): String =
    path.take(3) + (path(3).asDigit + 1).toString + path.drop(4)
  private def incStarSystem(path: String): String =
    path.take(2) + (path(2).asDigit + 1).toString + path.drop(3) + "0"
  private def incGalaxy(path: String): String = path.head + (path(1).asDigit + 1).toString + path.drop(4) + "00"
  private def incSuperCluster(path: String): String = (path.head.asDigit + 1).toString + path.tail + "000"

  private def computeNewPath(stats: Stats,
                             position: Int = 4)(path: String = incContinent(stats.currentPath)): String = {
    stats.list.get(path) match {
      case Some(_) =>
        path
      case None =>
        val zeroLast = path.take(position) + path.drop(position + 1).split("").map(_ => 0).mkString("")
        if (position == 4) computeNewPath(stats, position - 1)(incPlanet(zeroLast))
        else if (position == 3) computeNewPath(stats, position - 1)(incStarSystem(zeroLast))
        else if (position == 2) computeNewPath(stats, position - 1)(incGalaxy(zeroLast))
        else if (position == 1) computeNewPath(stats, position - 1)(incSuperCluster(zeroLast))
        else {
          // beat the game
          stats.currentPath
        }
    }
  }

  def incrementWinsAndTimedPlayed(tokenId: String, incrementWins: Boolean): Future[String] = {
    val newDate = new Date()
    (for {
      stats <- collection.find(equal(tokenIdLabel, tokenId))
      currentPath = stats.currentPath
      nextPath = if (incrementWins) computeNewPath(stats)() else stats.currentPath
      _ <- collection.updateOne(
        equal(tokenIdLabel, tokenId),
        combine(
          set(maxContinentLabel, updateMaxContinent(stats, nextPath)),
          set(currentPathLabel, if (incrementWins) nextPath else currentPath),
          inc(s"$listLabel.$currentPath.$timesPlayedLabel", 1),
          inc(s"$listLabel.$currentPath.$winsLabel", if (incrementWins) 1 else 0),
          set(s"$listLabel.$nextPath.$activeLabel",
              if (stats.list.keySet.contains(nextPath) && (stats.list(nextPath).active || incrementWins)) true
              else false),
          set(s"$listLabel.$currentPath.$lastPlayedLabel", newDate),
          inc(s"$listLabel.${currentPath.take(4)}.$timesPlayedLabel", 1),
          inc(s"$listLabel.${currentPath.take(4)}.$winsLabel", if (incrementWins) 1 else 0),
          set(
            s"$listLabel.${nextPath.take(4)}.$activeLabel",
            if (stats.list.keySet.contains(nextPath) && (stats.list(nextPath.take(4)).active || incrementWins)) true
            else false
          ),
          set(s"list.${currentPath.take(4)}.$lastPlayedLabel", newDate),
          inc(s"$listLabel.${currentPath.take(3)}.$timesPlayedLabel", 1),
          inc(s"$listLabel.${currentPath.take(3)}.$winsLabel", if (incrementWins) 1 else 0),
          set(
            s"$listLabel.${nextPath.take(3)}.$activeLabel",
            if (stats.list.keySet.contains(nextPath) && (stats.list(nextPath.take(3)).active || incrementWins)) true
            else false
          ),
          set(s"list.${currentPath.take(3)}.$lastPlayedLabel", newDate),
          inc(s"$listLabel.${currentPath.take(2)}.$timesPlayedLabel", 1),
          inc(s"$listLabel.${currentPath.take(2)}.$winsLabel", if (incrementWins) 1 else 0),
          set(
            s"$listLabel.${nextPath.take(2)}.$activeLabel",
            if (stats.list.keySet.contains(nextPath) && (stats.list(nextPath.take(2)).active || incrementWins)) true
            else false
          ),
          set(s"list.${currentPath.take(2)}.$lastPlayedLabel", newDate),
          inc(s"$listLabel.${currentPath.take(1)}.$timesPlayedLabel", 1),
          inc(s"$listLabel.${currentPath.take(1)}.$winsLabel", if (incrementWins) 1 else 0),
          set(
            s"$listLabel.${nextPath.take(1)}.$activeLabel",
            if (stats.list.keySet.contains(nextPath) && (stats.list(nextPath.take(1)).active || incrementWins)) true
            else false
          ),
          set(s"list.${currentPath.take(1)}.$lastPlayedLabel", newDate)
        )
      )
    } yield nextPath).toFuture().map(_.head)
  }

  def updatePath(tokenId: String, path: String): Future[Stats] = {
    collection.findOneAndUpdate(equal(tokenIdLabel, tokenId), set(currentPathLabel, path)).toFuture()
  }

  def unlock(tokenId: String): Future[Stats] =
    for {
      stats <- findStats(tokenId)
      updated = stats.map(ss => ss.copy(list = ss.list.mapValues(s => s.copy(active = true, wins = 1))))
      _ <- collection.replaceOne(equal(tokenIdLabel, tokenId), updated.get).toFuture()
    } yield updated.get

  def setIsSandbox(tokenId: String, bool: Boolean): Future[Stats] = {
    for {
      updated <- collection.findOneAndUpdate(equal(tokenIdLabel, tokenId), set(isSandboxLabel, bool)).toFuture()
    } yield updated.copy(isSandbox = Some(bool))
  }

  private final val nonAdminUserStatement = BsonDocument("""{ $match: { "user.isAdmin": false } }""")

  private final val lookupUserStatement = BsonDocument(s"""
                                                           |   { $$lookup: {
                                                           |        from: "playeraccount",
                                                           |        localField: "$tokenIdLabel",
                                                           |        foreignField: "$tokenIdLabel",
                                                           |        as: "user",
                                                           |      },
                                                           |    }
                       """.stripMargin)

  def levelStats(func: Option[String]): Future[Seq[LevelStats]] = {

    val _func = func.getOrElse("00000")

    val levelStatsStatement = BsonDocument(s"""
                                              |{
                                              |    $$group: {
                                              |      _id: '$$$listLabel.${_func}.$nameLabel' ,
                                              |      timesPlayed: { $$sum: '$$$listLabel.${_func}.$timesPlayedLabel' },
                                              |      timesPlayedAvg: { $$avg: '$$$listLabel.${_func}.$timesPlayedLabel' },
                                              |      timesPlayedMax: { $$max: '$$$listLabel.${_func}.$timesPlayedLabel' },
                                              |      wins: { $$sum: '$$$listLabel.${_func}.$winsLabel' },
                                              |      winsAvg: { $$avg: '$$$listLabel.${_func}.$winsLabel' },
                                              |      winsMax: { $$max: '$$$listLabel.${_func}.$winsLabel' },
                                              |    }
                                              |  }
       """.stripMargin)

    levelStatsCollection
      .aggregate(
        Seq(
          this.lookupUserStatement,
          this.nonAdminUserStatement,
          levelStatsStatement,
          BsonDocument(f"""{$$addFields: { id: '${_func}'}}""")
        )
      )
      .toFuture
  }

  def maxLevelStats: Future[Seq[MaxLevel]] = {

    maxLevelCollection
      .aggregate(
        Seq(
          this.lookupUserStatement,
          this.nonAdminUserStatement,
          BsonDocument(s"""{ $$sortByCount: '$$$maxContinentLabel' }""")
        )
      )
      .toFuture
  }

  collection.createIndex(ascending(tokenIdLabel)).toFuture().onComplete {
    case Success(_) =>
      aLogger.debug(SemanticLog.tags.index(collectionLabel, tokenIdLabel, "Created index"))
    case Failure(t) =>
      aLogger.error(SemanticLog.tags.index(collectionLabel, tokenIdLabel, t, "Failed to create index"))
  }
}
