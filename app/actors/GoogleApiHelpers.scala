package actors

import akka.http.scaladsl._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import configuration.GoogleApiConfig
import models.JwtToken
import org.joda.time.Instant
import spray.json._

import scala.concurrent.ExecutionContext
import scala.language.implicitConversions

object GoogleApiHelpers {

  def instantFromNow(milliseconds : Long) : Instant = Instant.now.plus(milliseconds)

  case class GoogleTokens(
      access_token : String,
      expires_in : Long,
      token_type : String,
      refresh_token : Option[String],
      id_token : JwtToken
  ) {
    def withAccessToken(access_token : String) : GoogleTokens = this.copy(access_token = access_token)
  }
  case class GoogleValueType(value : String, `type` : String)
  case class GoogleIcon(url : String, isDefault : Boolean)
  case class GooglePerson(kind : String, id : String, etag : String, emails : Seq[GoogleValueType], displayName : String, image : GoogleIcon)
}

trait GoogleApiHelpers extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val executionContext : ExecutionContext
  implicit val materializer : akka.stream.Materializer
  val http : HttpExt
  val config : GoogleApiConfig

}

