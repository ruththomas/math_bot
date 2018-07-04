package configuration

import akka.http.scaladsl.model.Uri

import scala.reflect.ClassTag
import scala.collection.JavaConverters._

object ConfigFactory {
  object mathbot {
    final val maxProgramSteps = "mathbot.maxProgramSteps"
    object oauth {
      object google {
        final val scopes : String = "mathbot.oauth.google.scopes"
        final val clientSecret : String = "mathbot.oauth.google.clientSecret"
        final val clientId : String = "mathbot.oauth.google.clientId"
        final val authRedirectUrl : String = "mathbot.oauth.google.authRedirectUrl"
        final val oauthUrl : String = "mathbot.oauth.google.oauthUrl"
      }
    }
  }
}

class ConfigFactory(playConfig: play.api.Configuration) {

  import ConfigFactory._


  private def exWrap[C](path : String, readers: Function1[String,Option[C]]* ) = {
    readers.map(r => r(path)).find(_.isDefined).flatten getOrElse {
      throw new MissingConfigurationException(path)
    }
  }


  def googleApiConfig(): GoogleApiConfig = {
    GoogleApiConfig(
      oauthTokenUri = exWrap(mathbot.oauth.google.oauthUrl, path => playConfig.getString(path).map(Uri(_))),
      authRedirectUri = exWrap(mathbot.oauth.google.authRedirectUrl, path => playConfig.getString(path).map(Uri(_))),
      clientId = exWrap(mathbot.oauth.google.clientId, playConfig.getString(_), sys.env.get(_)),
      clientSecret = exWrap(mathbot.oauth.google.clientSecret, playConfig.getString(_), sys.env.get(_)),
      scopes = exWrap(mathbot.oauth.google.scopes, playConfig.getStringList(_).map(s => s.asScala))
    )
  }

}
