package daos
import actors.messages.admin.AdminEvent
import com.google.inject.Inject
import com.mongodb.client.result._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{MongoCollection, MongoDatabase}

import scala.concurrent.{ExecutionContext, Future}

class EventsDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {

  import AdminEvent._

  final val collLabel = "events"

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[AdminEvent]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val coll: MongoCollection[AdminEvent] =
    mathbotDb
      .getCollection[AdminEvent](collLabel)
      .withCodecRegistry(codecRegistry)

  def insert(event: AdminEvent): Future[AdminEvent] =
    coll.insertOne(event).toFutureOption().map(_ => event)

  def replace(event: AdminEvent): Future[AdminEvent] =
    coll
      .replaceOne(equal(idLabel, event.id), event)
      .toFuture()
      .map(_ => event)
  def remove(id: String): Future[Option[DeleteResult]] = {

    coll.deleteOne(equal(idLabel, id)).toFutureOption()
  }

  def remove(event: AdminEvent): Future[Option[DeleteResult]] = {

    coll.deleteOne(equal(idLabel, event.id)).toFutureOption()

  }
  def getEvents: Future[Seq[AdminEvent]] = {

    coll.find().toFuture
  }
}
