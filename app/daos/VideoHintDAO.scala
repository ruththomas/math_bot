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
  import actors.VideoHintActor.calculateStars

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[HintsTaken](),
      Macros.createCodecProvider[HintTaken]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[HintsTaken] =
    mathbotDb.getCollection[HintsTaken](collectionLabel).withCodecRegistry(codecRegistry)

  def getHints(tokenId: TokenId): Future[Option[HintsTaken]] =
    collection.find(equal(tokenIdField, tokenId)).first().toFutureOption()

  def updateOrAdd(tokenId: TokenId, path: String, videoCount: Int): Future[HintsTaken] = {
    for {
      hints <- collection.find(equal(tokenIdField, tokenId)).first().toFutureOption()
      updatedOrAdded <- hints match {
        case Some(hintsTaken) =>
          hintsTaken.list.get(path) match {
            case Some(_) => updateHint(tokenId, path, videoCount)
            case None => insertHint(tokenId, path, videoCount)
          }
        case None => insert(tokenId, path, videoCount)
      }
    } yield updatedOrAdded
  }

  def insert(tokenId: TokenId, path: String, videoCount: Int): Future[HintsTaken] = {
    val timeStamp: Long = Instant.now.getEpochSecond
    val hintTaken = HintTaken(path, timeStamp, 1, calculateStars(3, videoCount))
    val hintsTaken = HintsTaken(tokenId, Map(path -> hintTaken))
    for {
      _ <- collection.insertOne(hintsTaken).toFuture()
    } yield hintsTaken
  }

  def insertHint(tokenId: TokenId, path: String, videoCount: Int): Future[HintsTaken] = {
    val timeStamp: Long = Instant.now.getEpochSecond
    val hintTaken = HintTaken(path, timeStamp, 1, calculateStars(3, videoCount))
    for {
      hintsTaken <- collection
        .findOneAndUpdate(
          equal(tokenIdField, tokenId),
          set(s"$listLabel.$path", hintTaken)
        )
        .toFuture()
        .map(ht => ht.copy(list = ht.list + (path -> hintTaken)))
    } yield hintsTaken
  }

  def updateHint(tokenId: TokenId, path: String, videoCount: Int): Future[HintsTaken] = {
    val timeStamp: Long = Instant.now.getEpochSecond
    for {
      stars <- collection.find(equal(tokenIdField, tokenId)).first().toFutureOption().map {
        case Some(hintsTaken) if hintsTaken.list isDefinedAt path =>
          calculateStars(hintsTaken.list(path).stars, videoCount)
        case _ => calculateStars(3, videoCount)
      }
      updated <- collection
        .findOneAndUpdate(
          equal(tokenIdField, tokenId),
          combine(
            set(s"$listLabel.$path.$continentIdLabel", path),
            set(s"$listLabel.$path.$timeStampLabel", timeStamp),
            inc(s"$listLabel.$path.$countLabel", 1),
            set(s"$listLabel.$path.$starsLabel", stars)
          )
        )
        .toFuture()
        .map(
          ht =>
            ht.copy(
              list = ht.list
                .map(h => if (h._1 == path) (h._1, h._2.copy(timeStamp = timeStamp, count = h._2.count + 1)) else h)
          )
        )
    } yield updated
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
