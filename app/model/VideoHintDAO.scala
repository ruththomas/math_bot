package model

import com.google.inject.Inject
import model.models.{HintTaken, VideoHint}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.result.{DeleteResult, UpdateResult}
import org.mongodb.scala.{Completed, MongoCollection, MongoDatabase, _}
import types.TokenId

import scala.concurrent.{ExecutionContext, Future}

class VideoHintDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {
  final val collectionLabel = "video-hints"

  import VideoHint._

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[VideoHint](),
      Macros.createCodecProvider[HintTaken]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[VideoHint] =
    mathbotDb.getCollection[VideoHint](collectionLabel).withCodecRegistry(codecRegistry)

  def insert(videoHint: VideoHint): Future[Option[Completed]] =
    collection.insertOne(videoHint).toFutureOption()

  def getHints(tokenId: TokenId): Future[Option[VideoHint]] =
    collection.find(equal(tokenIdField, tokenId)).first().toFutureOption()

  def delete(tokenId: TokenId): Future[Option[DeleteResult]] =
    collection.deleteOne(equal(tokenIdField, tokenId)).toFutureOption()

  def update(videoHint: VideoHint): Future[Option[UpdateResult]] =
    collection
      .replaceOne(equal(tokenIdField, videoHint.tokenId), videoHint)
      .toFutureOption()
}
