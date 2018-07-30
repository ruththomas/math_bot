package modules
import actors.{ ActorTags, GoogleOAuth }
import com.google.inject.{ AbstractModule, Provides }
import configuration.{ ActorConfig, ConfigFactory, GoogleApiConfig }
import org.mongodb.scala.{ MongoClient, MongoDatabase }
import com.google.inject.{AbstractModule, Inject, Provides}
import org.mongodb.scala.{MongoClient, MongoDatabase}
import play.api.libs.concurrent.AkkaGuiceSupport

import com.typesafe.config.ConfigFactory
import play.api.Configuration

class Module extends AbstractModule with AkkaGuiceSupport {
  override def configure() = {
    bindActor[GoogleOAuth](ActorTags.googleOAuth)

  }


  override def configure(): Unit = {}
  val config: Configuration = new Configuration(ConfigFactory.load())

  @Provides
  def provideMongoDatabase(): MongoDatabase = {
    val DB_NAME = config.underlying.getString("mongodb.name")
    val MONGO_URL = config.underlying.getString("mongodb.url")
    // To directly connect to the default server localhost on port 27017
    val mongoClient: MongoClient = MongoClient(MONGO_URL)
    mongoClient.getDatabase(DB_NAME)
  }

  @Provides
  def provideGoogleApiConfig(configFactory: ConfigFactory) : GoogleApiConfig = {
    configFactory.googleApiConfig()
  }

  @Provides
  def provideActorConfig(configFactory: ConfigFactory) : ActorConfig = {
    configFactory.actorConfig()
  }

}
