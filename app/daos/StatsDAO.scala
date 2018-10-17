package daos

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

  private final val tokenIdLabel: TokenId = "tokenId"
  private final val starSystemLabel: String = "starSystem"
  private final val planetLabel: String = "planet"
  private final val continentLabel: String = "continent"

  val collection: MongoCollection[Stats] =
    mathbotDb.getCollection[Stats](collectionLabel).withCodecRegistry(codecRegistry)

  def insert(stats: Stats): Future[Option[Completed]] =
    collection.insertOne(stats).toFutureOption()

  def findStats(tokenId: TokenId): Future[Option[Stats]] =
    collection.find(equal(tokenIdLabel, tokenId)).first().toFutureOption()

  def gatherGalaxy(tokenId: TokenId, key: String): Future[Option[Map[TokenId, Map[String, LayerStatistic]]]] =
    collection
      .find(equal(tokenIdLabel, tokenId))
      .first()
      .toFutureOption()
      .map {
        _.map { stats =>
          Map(
            "galaxy" -> Map(key -> stats.galaxyStats(key)),
            "starSystems" -> stats.starSystemStats.filterKeys(_.take(key.length).contains(key))
          )
        }
      }

  def gatherStarSystem(tokenId: TokenId, key: String): Future[Option[Map[TokenId, Map[String, LayerStatistic]]]] =
    collection.find(equal(tokenIdLabel, tokenId)).first().toFutureOption().map {
      _.map { stats =>
        Map(
          "starSystem" -> Map(key -> stats.starSystemStats(key)),
          "planets" -> stats.planetStats.filterKeys(_.take(key.length).contains(key)),
          "continents" -> stats.continentStats.filterKeys(_.take(key.length).contains(key))
        )
      }
    }

  def updateLevel(tokenId: TokenId,
                  starSystemInd: Int,
                  planetInd: Int,
                  continentInd: Int): Future[Option[UpdateResult]] =
    collection
      .updateOne(
        equal(tokenIdLabel, tokenId),
        combine(set(starSystemLabel, starSystemInd), set(planetLabel, planetInd), set(continentLabel, continentInd))
      )
      .toFutureOption()
}
