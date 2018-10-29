package daos

import java.time.Instant

import actors.messages.level.{HintTaken, HintsTaken}
import com.google.inject.Inject
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.{MongoCollection, MongoDatabase, _}
import types.TokenId

import scala.concurrent.{ExecutionContext, Future}

class VideoHintDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {
  final val collectionLabel: String = "video_hints"

  import HintTaken._
  import HintsTaken._

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[HintsTaken](),
      Macros.createCodecProvider[HintTaken]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[HintsTaken] =
    mathbotDb.getCollection[HintsTaken](collectionLabel).withCodecRegistry(codecRegistry)

  def insert(tokenId: TokenId, path: String): Future[HintsTaken] = {
    val timeStamp: Long = Instant.now.getEpochSecond
    val hintTaken = HintTaken(path, timeStamp, 1, 3)
    val hintsTaken = HintsTaken(tokenId, Map(path -> hintTaken))
    for {
      _ <- collection.insertOne(hintsTaken).toFuture()
    } yield hintsTaken
  }

  def getHints(tokenId: TokenId): Future[Option[HintsTaken]] =
    collection.find(equal(tokenIdField, tokenId)).first().toFutureOption()

  def update(tokenId: TokenId, path: String): Future[Option[HintsTaken]] = {
    val timeStamp: Long = Instant.now.getEpochSecond
    collection
      .findOneAndUpdate(
        equal(tokenIdField, tokenId),
        combine(
          set(s"$listLabel.$path.$timeStampLabel", timeStamp),
          inc(s"$listLabel.$path.$countLabel", 1)
        )
      )
      .toFutureOption()
      .map( // doing this because it is returning the pre-updated hints
        _.map(
          ht =>
            ht.copy(
              list = ht.list
                .map(h => if (h._1 == path) (h._1, h._2.copy(timeStamp = timeStamp, count = h._2.count + 1)) else h)
          )
        )
      )
  }

  def replaceList(tokenId: TokenId, list: Map[String, HintTaken]): Future[HintsTaken] = {
    collection
      .findOneAndUpdate(equal(tokenIdField, tokenId), set(listLabel, list))
      .toFuture()
  }

  def getHint(tokenId: TokenId, path: String): Future[Option[HintTaken]] =
    collection.find(equal(s"$tokenIdField", tokenId)).first().toFutureOption().map(_.map(_.list.get(path)).head)

  def reset(tokenId: TokenId, path: String): Future[HintTaken] = {
    collection
      .findOneAndUpdate(
        equal(
          tokenIdField,
          tokenId
        ),
        combine(
          set(s"$listLabel.$path.$countLabel", 0)
        )
      )
      .toFuture()
      .map(ht => ht.list(path).copy(count = 0)) // doing this because it is returning the pre-updated hints
  }
}
