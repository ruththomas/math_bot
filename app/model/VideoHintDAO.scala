package model

import com.google.inject.Inject
import model.models.{HintsTaken, VideoHint}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.{DEFAULT_CODEC_REGISTRY, Macros}
import org.mongodb.scala.{MongoCollection, MongoDatabase}

import scala.concurrent.ExecutionContext

class VideoHintDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {
  final val collectionLabel = "video-hints"

  import VideoHint._

  val codecRegistry: CodecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[VideoHint](),
      Macros.createCodecProvider[HintsTaken]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  val collection: MongoCollection[VideoHint] =
    mathbotDb.getCollection[VideoHint](collectionLabel).withCodecRegistry(codecRegistry)
}
