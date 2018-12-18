package daos
import actors.messages.admin.Event
import com.google.inject.Inject
import com.mongodb.client.result._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{Completed, MongoCollection, MongoDatabase}

import scala.concurrent.{ExecutionContext, Future}

class EventsDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {

  import Event._

  final val collLabel = "events"

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[Event]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val coll: MongoCollection[Event] =
    mathbotDb
      .getCollection[Event](collLabel)
      .withCodecRegistry(codecRegistry)

  def insert(event: Event): Future[Option[Completed]] =
    coll.insertOne(event).toFutureOption()

  def replace(event: Event): Future[Event] =
    coll
      .replaceOne(equal(titleLabel, event.title), event)
      .toFuture()
      .map(_ => event)
  def remove(title: String): Future[Option[DeleteResult]] = {

    coll.deleteOne(equal(titleLabel, title)).toFutureOption()
  }

  def getEvents: Future[Seq[Event]] = {

    coll.find().toFuture
  }
}
