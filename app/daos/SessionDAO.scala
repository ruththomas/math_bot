package daos

import com.google.inject.Inject
import daos.codecs.SecureIdentifierCodec
import loggers.SemanticLog
import models.JwtToken
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.mongodb.scala.MongoDatabase
import org.mongodb.scala.bson.codecs.Macros
import utils.SecureIdentifier

import scala.concurrent.ExecutionContext

class SessionDAO @Inject()(
    override val db: MongoDatabase,
    aLogger : SemanticLog,
    aCodecs: Seq[Codec[_]] = Seq.empty[Codec[_]],
    aProviders: Seq[CodecProvider]
)(override implicit val ec : ExecutionContext) extends Storage[SecureIdentifier, JwtToken](
      'session,
      'sessionId,
      valueField = 'token,
      _ => new SecureIdentifierCodec,
      registry => Macros.createCodec[JwtToken](registry),
      aProviders,
      aCodecs
    ) {

  override protected val logger : SemanticLog = aLogger.withClass[SessionDAO]()
}
