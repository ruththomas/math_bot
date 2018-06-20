package model

import com.google.inject.Inject
import dataentry.utility.SecureIdentifier
import model.models.AuthSources.{ AuthSource, AuthSourceCodec }
import model.models.SessionAuth
import org.bson.codecs.configuration.CodecRegistries.{ fromProviders, fromRegistries }
import org.bson.codecs.configuration.{ CodecRegistries, CodecRegistry }
import org.mongodb.scala.bson.codecs.{ DEFAULT_CODEC_REGISTRY, Macros }
import org.mongodb.scala.MongoDatabase

import scala.concurrent.ExecutionContext

class SessionDAO @Inject()(mathbotDb: MongoDatabase)(implicit ec: ExecutionContext) {


  final val collection = "session"

  private val codecRegistry : CodecRegistry =
    fromRegistries(
      fromProviders(
        Macros.createCodecProvider[SessionAuth]
      ),
      CodecRegistries.fromCodecs(new AuthSourceCodec),
      DEFAULT_CODEC_REGISTRY
    )

  def insert(sid : SecureIdentifier) = ???

}
