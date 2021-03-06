package daos
import actors.messages.admin.AdminEvent
import com.google.inject.Inject
import loggers.SemanticLog
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.{IndexOptions, Indexes}
import org.mongodb.scala.{MongoCollection, MongoDatabase}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class EventsDAO @Inject()(mathbotDb: MongoDatabase, aLogger: SemanticLog)(implicit ec: ExecutionContext) {

  import AdminEvent._

  final val collectionLabel = "events"

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[AdminEvent]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[AdminEvent] =
    mathbotDb
      .getCollection[AdminEvent](collectionLabel)
      .withCodecRegistry(codecRegistry)

  def insert(event: AdminEvent): Future[AdminEvent] =
    collection
      .insertOne(event)
      .toFuture()
      .map(_ => event)

  def replace(event: AdminEvent): Future[AdminEvent] =
    collection
      .replaceOne(equal(idLabel, event.id), event)
      .toFuture()
      .map(_ => event)
  def remove(id: String): Future[Boolean] = {
    for {
      res <- collection.deleteOne(equal(idLabel, id)).toFuture
    } yield {
      0 < res.getDeletedCount
    }
  }

  def remove(event: AdminEvent): Future[Boolean] = {
    for {
      res <- collection.deleteOne(equal(idLabel, event.id)).toFuture
    } yield {
      0 < res.getDeletedCount
    }
  }
  def getEvents: Future[Seq[AdminEvent]] = {
    collection.find().toFuture
  }

  // creating index on id field as not using _id womp

  collection
    .createIndex(Indexes.ascending(idLabel), IndexOptions().unique(true))
    .toFuture()
    .onComplete {
      case Success(_) =>
        aLogger.debug(SemanticLog.tags.index(collectionLabel, idLabel, "Created index"))
      case Failure(t) =>
        aLogger.error(SemanticLog.tags.index(collectionLabel, idLabel, t, "Failed to create index"))
    }
}
