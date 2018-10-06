package controllers

import com.google.inject.Inject
import javax.inject.Singleton
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class SpaController @Inject()(assets: Assets)(implicit ec: ExecutionContext) extends Controller {

  def index = {
    assets.at("/public", "index.html")
  }

  def assetOrDefault(resource: String) = {
    if (resource.contains(".")) assets.at("/public", resource) else index
  }
}
