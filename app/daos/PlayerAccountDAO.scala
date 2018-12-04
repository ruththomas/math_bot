package daos

import java.util.Date

import actors.messages.playeraccount.{SignupDate, UserAccountSignups}
import com.google.inject.Inject
import loggers.SemanticLog
import models._
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.{CodecProvider, CodecRegistries}
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.bson.{BsonDateTime, BsonDocument}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Indexes._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.{Completed, MongoCollection, MongoDatabase, _}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class PlayerAccountDAO @Inject()(
                                  db: MongoDatabase,
                                  aLogger: SemanticLog,
                                  aCodecs: Seq[Codec[_]] = Seq.empty[Codec[_]],
                                  aProviders: Seq[CodecProvider]
                                )(implicit val ec: ExecutionContext) {
  private val collectionLabel = 'playeraccount
  private val tokenIdLabel = 'tokenId
  private val emailLabel = 'email
  private val isAdminLabel = 'isAdmin
  private val lastAccess = 'lastAccess
  private val timesAccessed = 'timesAccessed
  private val maxLevel = 'maxLevel
  private val maxStep = 'maxStep
  private val createdLabel = 'created


  private val codecRegistry = fromRegistries(
    fromProviders(
      Seq(
        // Macros.createCodecProvider[PlayerAccount](),
        Macros.createCodecProvider[SignupDate](),
        Macros.createCodecProvider[UserAccountSignups]()
      ) ++ aProviders: _*
    ),
    CodecRegistries.fromCodecs(aCodecs: _*),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[PlayerAccount] =
    db.getCollection[PlayerAccount](collectionLabel.name)
      .withCodecRegistry(codecRegistry)


  val userAccountSignupCollection: MongoCollection[UserAccountSignups] =
    db.getCollection[UserAccountSignups](collectionLabel.name)
      .withCodecRegistry(codecRegistry)


  def put(pa: PlayerAccount): Future[Completed] = collection.insertOne(pa).toFuture()

  def find(tokenId: String): Future[Option[PlayerAccount]] =
    collection.find(equal(tokenIdLabel.name, tokenId)).headOption()

  def setAdmin(tokenId: Option[String], email: Option[String], isAdmin: Boolean): Future[Option[PlayerAccount]] =
    for {
      result <- collection
        .findOneAndUpdate(tokenId match {
          case Some(id) => equal(tokenIdLabel.name, id)
          case None => equal(emailLabel.name, email.getOrElse(""))
        }, set(isAdminLabel.name, isAdmin))
        .toFutureOption()
    } yield result

  def updateAccess(tokenId: String): Future[Option[PlayerAccount]] =
    for {
      result <- collection
        .findOneAndUpdate(equal(tokenIdLabel.name, tokenId),
          combine(currentDate(lastAccess.name), inc(timesAccessed.name, 1)))
        .toFutureOption()
    } yield result

  def updateMaxLevelAndStep(tokenId: String, ml: String, ms: String): Future[Option[PlayerAccount]] =
    for {
      result <- collection
        .findOneAndUpdate(equal(tokenIdLabel.name, tokenId), combine(set(maxLevel.name, ml), set(maxStep.name, ms)))
        .toFutureOption()
    } yield result

  collection.createIndex(ascending(tokenIdLabel.name)).toFuture().onComplete {
    case Success(_) =>
      aLogger.debug(SemanticLog.tags.index(collectionLabel, tokenIdLabel, "Created index"))
    case Failure(t) =>
      aLogger.error(SemanticLog.tags.index(collectionLabel, tokenIdLabel, t, "Failed to create index"))
  }

  def count: Future[Long] = collection.count().toFuture()


  private final val signupsPerDayQueryText =
    """
      |
      |{$group: {
      |      _id: {month: {$month: "$created"}, day: {$dayOfMonth: "$created"}, year: {$year: "$created"}},
      |      signups: {$sum: 1},
      |    }
      |  }""".stripMargin

  private final val signupsPerDayQuery = BsonDocument(this.signupsPerDayQueryText)

  def signupsPerDay: Future[Seq[UserAccountSignups]] = {


    userAccountSignupCollection.aggregate(List(
      this.signupsPerDayQuery
    )).toFuture()

  }


  private final val DAY_IN_MS = 1000 * 60 * 60 * 24
  private final val sevenDays = 7 * DAY_IN_MS

  def last7DaysLoginCount: Future[Long] = {

    val date = BsonDateTime(new Date(new Date().getTime - this.sevenDays))

    collection.count(gt(lastAccess.name, date)).toFuture

  }


}
