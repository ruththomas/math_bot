package daos

import actors.messages.level.{Function, Functions}
import com.google.inject.Inject
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.{Completed, MongoCollection, MongoDatabase, SingleObservable}
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.result.UpdateResult
import types.TokenId

import scala.concurrent.{ExecutionContext, Future}

class FunctionsDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {
  import Function._
  final val collectionLabel = "functions"

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[Functions](),
      Macros.createCodecProvider[Function]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[Functions] =
    mathbotDb.getCollection[Functions](collectionLabel).withCodecRegistry(codecRegistry)

  def find(tokenId: TokenId): Future[Option[Functions]] =
    collection.find(equal(tokenIdLabel, tokenId)).first().toFutureOption()

  def insert(functions: Functions): Future[Option[Completed]] =
    collection.insertOne(functions).toFutureOption()

  def updateFunction(tokenId: TokenId, function: Function): Future[Option[Functions]] =
    collection
      .findOneAndUpdate(
        equal(tokenIdLabel, tokenId),
        set(
          s"list.${function.created_id}",
          function
        )
      )
      .toFutureOption()

  def replaceAll(tokenId: TokenId, functions: Functions): Future[Option[UpdateResult]] =
    collection.replaceOne(equal(tokenIdLabel, tokenId), functions).toFutureOption()
}
