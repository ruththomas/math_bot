package configuration

import akka.http.scaladsl.model.Uri
import akka.util.Timeout
import com.google.inject.Inject

import scala.collection.JavaConverters._
import scala.concurrent.duration.{ Duration, FiniteDuration }

object ConfigFactory {
  object mathbot {
    final val maxProgramSteps = "mathbot.maxProgramSteps"
    object oauth {
      object google {
        final val scopes: String = "mathbot.oauth.google.scopes"
        final val clientSecret: String = "mathbot.oauth.google.clientSecret"
        final val clientId: String = "mathbot.oauth.google.clientId"
        final val redirectUrl: String = "mathbot.oauth.google.redirectUrl"
        final val authUrl: String = "mathbot.oauth.google.authUrl"
        final val tokenUrl : String = "mathbot.oauth.google.tokenUrl"
        // Location of the PEM format certificates used to verify JWT tokens
        final val pemUrl: String = "mathbot.oauth.google.pemUrl"
      }
    }
    object actors {
      final val timeout: String = "mathbot.actors.timeout"
    }

    object mongodb {
      val name : String = "mathbot.mongodb.name"
      val url : String = "mathbot.mongodb.url"
    }
  }
}

class ConfigFactory @Inject()(playConfig: play.api.Configuration) {

  import ConfigFactory._

  private def exWrap[C](path: String, readers: Function1[String, Option[C]]*) = {
    readers.map(r => r(path)).find(_.isDefined).flatten getOrElse {
      throw new MissingConfigurationException(path)
    }
  }

  private def envGet(path : String) =
    sys.env.get(path.replace(".", "_"))

  def googleApiConfig(): GoogleApiConfig = {
    GoogleApiConfig(
      oauthUrl = exWrap(mathbot.oauth.google.authUrl, path => playConfig.getString(path).map(Uri(_))),
      authRedirectUri = exWrap(mathbot.oauth.google.redirectUrl, path => playConfig.getString(path).map(Uri(_))),
      authTokenUrl = exWrap(mathbot.oauth.google.tokenUrl, path => playConfig.getString(path).map(Uri(_))),
      clientId = exWrap(mathbot.oauth.google.clientId, envGet(_), playConfig.getString(_)),
      clientSecret = exWrap(mathbot.oauth.google.clientSecret, envGet(_), playConfig.getString(_)),
      scopes = exWrap(mathbot.oauth.google.scopes, playConfig.getStringList(_).map(s => s.asScala)),
      oauthPemUri = exWrap(mathbot.oauth.google.pemUrl, path => playConfig.getString(path).map(Uri(_)))
    )
  }

  def actorConfig(): ActorConfig = {
    ActorConfig(
      timeout =
        exWrap(mathbot.actors.timeout, path => playConfig.getString(path).map(s => Timeout(Duration(s).asInstanceOf[FiniteDuration])))
    )
  }

  def mongoConfig(): MongoConfig = {
    MongoConfig(
      name = exWrap(mathbot.mongodb.name, name => playConfig.getString(name)),
      url = exWrap(mathbot.mongodb.url, url => playConfig.getString(url))
    )
  }

}
