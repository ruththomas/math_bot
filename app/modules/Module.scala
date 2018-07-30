package modules
import actors.{ ActorTags, GoogleOAuth }
import com.google.inject.{ AbstractModule, Provides }
import configuration.{ ActorConfig, ConfigFactory, GoogleApiConfig }
import org.mongodb.scala.{ MongoClient, MongoDatabase }
import play.api.libs.concurrent.AkkaGuiceSupport

class Module extends AbstractModule with AkkaGuiceSupport {
  override def configure() = {
    bindActor[GoogleOAuth](ActorTags.googleOAuth)

  }

  @Provides
  def provideMongoDatabase(configFactory: ConfigFactory): MongoDatabase = {
    val name = configFactory.mongoConfig.name
    val url = configFactory.mongoConfig.url
    // To directly connect to the default server localhost on port 27017
    val mongoClient: MongoClient = MongoClient(url.toString())
    mongoClient.getDatabase(name)
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
