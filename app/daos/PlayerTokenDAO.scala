package daos

import com.google.inject.Inject
import models.{CurrentStats, FuncToken, Lambdas, PlayerToken, StepToken}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.{CodecRegistries, CodecRegistry}
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.result.{DeleteResult, UpdateResult}
import org.mongodb.scala.{Completed, _}
import types.TokenId

import scala.concurrent.{ExecutionContext, Future}

class PlayerTokenDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {
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

  def getToken(tokenId: TokenId): Future[Option[PlayerToken]] =
    collection.find(equal(tokenIdField, tokenId)).first().toFutureOption()
  def delete(tokenId: TokenId): Future[Option[DeleteResult]] =
    collection.deleteOne(equal(tokenIdField, tokenId)).toFutureOption()

  def updateToken(playerToken: PlayerToken): Future[Option[UpdateResult]] =
    collection
      .replaceOne(equal(tokenIdField, playerToken.token_id), playerToken)
      .toFutureOption()

  def getTokenCount: Future[String] = collection.count().toFuture().map(_.toString)
}
