package daos

import com.google.inject.Inject
import loggers.SemanticLog
import models.Auth0LegacyUser
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.{CodecProvider, CodecRegistries}
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Indexes._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.{MongoDatabase, _}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class Auth0LegacyDao @Inject()(val db: MongoDatabase,
                               aLogger: SemanticLog,
                               aCodecs: Seq[Codec[_]] = Seq.empty[Codec[_]],
                               aProviders: Seq[CodecProvider])(implicit val ec: ExecutionContext) {

  private val collectionLabel = 'auth0legacy
  private val emailColumnLabel = 'email
  private val migratedColumnLabel = 'migrated
  private val userIdColumnLabel = 'user_id

  case class UnmigratedSocialUsers(count: Long)

  private val codecRegistry = fromRegistries(
    fromProviders(
      Seq(
        Macros.createCodecProvider[UnmigratedSocialUsers]()
      ) ++ aProviders: _*
    ),
    CodecRegistries.fromCodecs(aCodecs: _*),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[Auth0LegacyUser] =
    db.getCollection[Auth0LegacyUser](collectionLabel.name).withCodecRegistry(codecRegistry)

  val unmigratedColl: MongoCollection[UnmigratedSocialUsers] =
    db.getCollection[UnmigratedSocialUsers](collectionLabel.name)
      .withCodecRegistry(codecRegistry)
  def find(email: String): Future[Option[Auth0LegacyUser]] =
    collection.find(equal(emailColumnLabel.name, email)).toFuture().map(_.headOption)

  def markMigrated(email: String): Future[Option[Auth0LegacyUser]] =
    collection
      .findOneAndUpdate(equal(emailColumnLabel.name, email), set(migratedColumnLabel.name, true))
      .toFutureOption()

  private final val unmigratedUsersStatement = BsonDocument(s"{${migratedColumnLabel.name}: false}")

  private def countUnmigratedLocal: Future[Long] = {

    collection.count(unmigratedUsersStatement).toFuture
  }

  def countUnmigrated: Future[Long] = {

    for {
      local <- this.countUnmigratedLocal
      social <- this.countUnmigratedSocial
    } yield local + social
  }

  private final val matchSocialUsersStatement = BsonDocument(
    """{$match: { user_id: {$not:/^mathbot\|.*|^auth0\|.*/ } }}"""
  )

  private def countUnmigratedSocial: Future[Long] = {

    unmigratedColl
      .aggregate(
        Seq(
          matchSocialUsersStatement,
          BsonDocument(s"""
                       |{ $$lookup: {
                       |          from: "playeraccount",
                       |          localField: "${userIdColumnLabel.name}",
                       |          foreignField: "tokenId",
                       |          as: "user",
                       |        }
                       |}
                     """.stripMargin),
          BsonDocument("""{ $match : { user: {$eq: []} }},"""),
          BsonDocument("""{ $count: 'count'}""")
        )
      )
      .toFuture()
      .map(result => if (result.isEmpty) 0 else result.head.count)

  }

  collection.createIndex(ascending(emailColumnLabel.name)).toFuture().onComplete {
    case Success(_) =>
      aLogger.debug(SemanticLog.tags.index(collectionLabel.name, emailColumnLabel.name, "Created index"))
    case Failure(t) =>
      aLogger.error(SemanticLog.tags.index(collectionLabel.name, emailColumnLabel.name, t, "Failed to create index"))
  }
//  collection.createIndex(text(userIdColumnLabel.name)).toFuture().onComplete {
//    case Success(_) =>
//      aLogger.debug(SemanticLog.tags.index(collectionLabel.name, userIdColumnLabel.name, "Created index"))
//    case Failure(t) =>
//      aLogger.error(SemanticLog.tags.index(collectionLabel.name, userIdColumnLabel.name, t, "Failed to create index"))
//  }
}
