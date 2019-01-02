package daos

import actors.messages.level.Stats.tokenIdLabel
import com.google.inject.Inject
import loggers.SemanticLog
import models.StepToken
import models.deprecatedPlayerToken.{CurrentStats, FuncToken, Lambdas, PlayerToken}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.{CodecRegistries, CodecRegistry}
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Indexes.ascending
import org.mongodb.scala.result.{DeleteResult, UpdateResult}
import org.mongodb.scala.{Completed, _}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class PlayerTokenDAO @Inject()(mathbotDb: MongoDatabase, aLogger: SemanticLog)(implicit ec: ExecutionContext) {
  final val collectionLabel = "tokens"

  import PlayerToken._

  val codecRegistry: CodecRegistry =
    fromRegistries(
      fromProviders(
        Macros.createCodecProvider[PlayerToken](),
        Macros.createCodecProvider[Lambdas](),
        Macros.createCodecProvider[CurrentStats](),
        Macros.createCodecProvider[FuncToken](),
        Macros.createCodecProvider[StepToken]()
      ),
      CodecRegistries.fromCodecs(new utils.SecureIdentifier.SecureIdentifierCodec),
      DEFAULT_CODEC_REGISTRY
    )

  val collection: MongoCollection[PlayerToken] =
    mathbotDb.getCollection[PlayerToken](collectionLabel).withCodecRegistry(codecRegistry)

  def insert(playerToken: PlayerToken): Future[Option[Completed]] =
    collection.insertOne(playerToken).toFutureOption()

  def getToken(tokenId: String): Future[Option[PlayerToken]] =
    collection.find(equal(tokenIdField, tokenId)).first().toFutureOption()
  def delete(tokenId: String): Future[Option[DeleteResult]] =
    collection.deleteOne(equal(tokenIdField, tokenId)).toFutureOption()

  def updateToken(playerToken: PlayerToken): Future[Option[UpdateResult]] =
    collection
      .replaceOne(equal(tokenIdField, playerToken.token_id), playerToken)
      .toFutureOption()

  def getTokenCount: Future[String] = collection.count().toFuture().map(_.toString)

  collection.createIndex(ascending(tokenIdLabel)).toFuture().onComplete {
    case Success(_) =>
      aLogger.debug(SemanticLog.tags.index(collectionLabel, tokenIdLabel, "Created index"))
    case Failure(t) =>
      aLogger.error(SemanticLog.tags.index(collectionLabel, tokenIdLabel, t, "Failed to create index"))
  }
}
