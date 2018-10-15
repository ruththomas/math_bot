package daos

import com.google.inject.Inject
import models.Lambdas
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.MongoDatabase
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}

import scala.concurrent.ExecutionContext

class LambdasDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {
  final val collectionLabel = "lambdas"

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[Lambdas](),
      Macros.createCodecProvider[Lambdas]()
    ),
    DEFAULT_CODEC_REGISTRY
  )
}
