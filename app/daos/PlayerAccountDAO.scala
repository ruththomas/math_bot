package daos

import java.util.Date

import actors.messages.playeraccount.{MaxLevel, SignupDate, UserAccountSignups}
import com.google.inject.Inject
import loggers.SemanticLog
import models._
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.{CodecProvider, CodecRegistries}
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.bson.{BsonBoolean, BsonDateTime, BsonDocument}
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
  private val subLabel = 'sub
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
        Macros.createCodecProvider[UserAccountSignups](),
        Macros.createCodecProvider[MaxLevel]()
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

  val _collection: MongoCollection[Document] =
    db.getCollection[Document](collectionLabel.name)
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

  private final val nonAdminAccountStatement: BsonDocument = BsonDocument(
    s"""{ $$match: { ${isAdminLabel.name}: false }}"""
  )

  /*

    Non admin user grouped by day
    @returns signup count, and timesAccessed count
   */
  def signupsPerDay: Future[Seq[UserAccountSignups]] = {

    userAccountSignupCollection
      .aggregate(
        Seq(
          this.nonAdminAccountStatement,
          BsonDocument(f"""
                          |{$$group: {
                          |      _id: {
                          |         month: {$$month: "$$${createdLabel.name}"},
                          |         day: {$$dayOfMonth: "$$${createdLabel.name}"},
                          |         year: {$$year: "$$${createdLabel.name}"}
                          |      },
                          |      signups: {$$sum: 1},
                          |    }
                          |  }""".stripMargin)
        )
      )
      .toFuture

  }

  /*
    @return non admin user logged in past X days
   */
  def lastXDaysLoginCount(days: Option[Int]): Future[Long] = {

    val _days: Int = days.getOrElse(7)
    val date: BsonDateTime = BsonDateTime(new Date(new Date().getTime - 1000 * 60 * 60 * 24 * _days))

    collection
      .count(combine(gte(lastAccess.name, date), equal(isAdminLabel.name, false)))
      .toFuture()
  }

  /*

  fixme: query is expensive cannot use in prod
   */

  def activeUserCount: Future[Int] = {

    collection
      .aggregate(
        Seq(
          this.nonAdminAccountStatement,
          BsonDocument(f"""
                         |   { $$lookup: {
                         |        from: "session",
                         |        localField: "${subLabel.name}",
                         |        foreignField: "token.${subLabel.name}",
                         |        as: "foundToken",
                         |      }
                         |    }
                       """.stripMargin)
        )
      )
      .toFuture()
      .map(_.length)

  }

  /*

    Non admin user count
   */

  def userCount: Future[Long] = {

    collection.count(equal(isAdminLabel.name, BsonBoolean(false))).toFuture()
  }

}
