package daos
import actors.messages.admin.AdminEvent
import com.google.inject.Inject
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{MongoCollection, MongoDatabase}

import scala.concurrent.{ExecutionContext, Future}

class EventsDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {

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
}
