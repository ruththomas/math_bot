package modules
import com.google.inject.{AbstractModule, Provides}
import org.mongodb.scala.{MongoClient, MongoDatabase}
import play.api.libs.concurrent.AkkaGuiceSupport

class Module extends AbstractModule with AkkaGuiceSupport {
  override def configure() = {}

  @Provides
  def provideMongoDatabase(): MongoDatabase = {
    // To directly connect to the default server localhost on port 27017
    val mongoClient: MongoClient = MongoClient()
    mongoClient.getDatabase("mathbot-tokens")
  }
}
