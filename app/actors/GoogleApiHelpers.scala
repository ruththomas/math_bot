package dataentry.actors.models

import java.util.Optional

import akka.http.scaladsl._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.util.FastFuture
import org.joda.time.Instant
import spray.json._
import utils.GoogleApiConfig

import scala.compat.java8.OptionConverters._
import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Success
import scala.language.implicitConversions

object GoogleApiHelpers {

  def instantFromNow(milliseconds : Long) = Instant.now.plus(milliseconds)

  case class GoogleTokens(
      access_token : String,
      expires_in : Long,
      token_type : String,
      refresh_token : Option[String],
      expires_on : Option[Instant] = None
  ) {
    def withAccessToken(access_token : String) = this.copy(access_token = access_token)
    def isExpired(buffer : Long = 300) : Boolean = expires_on.forall(ex => ex.isBefore(Instant.now().plus(buffer * 1000)))
    def isPastHalfLife = expires_on.forall(ex => ex.isBefore(Instant.now().plus((expires_in / 2) * 1000)))
    def applyCreationTime(epoch : Long) = this.copy(expires_on = Some(new Instant(epoch + this.expires_in)))
  }
  case class GoogleValueType(value : String, `type` : String)
  case class GoogleIcon(url : String, isDefault : Boolean)
  case class GooglePerson(kind : String, id : String, etag : String, emails : Seq[GoogleValueType], displayName : String, image : GoogleIcon)
}

trait GoogleApiHelpers extends SprayJsonSupport with DefaultJsonProtocol {

  import GoogleApiHelpers._

  implicit val executionContext : ExecutionContext
  implicit val materializer : akka.stream.Materializer
  val http : HttpExt
  val config : GoogleApiConfig

  implicit object InstantFormat extends RootJsonFormat[Instant] {
    override def read(json : JsValue) = json match {
      case JsNumber(millis) => new Instant(millis)
      case _ => deserializationError("Milliseconds needed for joda time instant")
    }

    override def write(obj : Instant) = JsNumber(obj.getMillis)
  }

  implicit val googleValueTypeFormat = jsonFormat2(GoogleValueType)
  implicit val googleTokensFormat = jsonFormat5(GoogleTokens)
  implicit val googleImageFormat = jsonFormat2(GoogleIcon)
  implicit val googleProfileFormat = jsonFormat6(GooglePerson)

  def unmarshal[G](response : HttpResponse, jsonParser : RootJsonFormat[G]) : Future[Either[G, (StatusCode, String)]] =
    response match {
      case HttpResponse(StatusCodes.OK, _, entity, _) =>
        for {
          json <- Unmarshal(entity).to[String]
        } yield {
          Left(jsonParser.read(json.parseJson))
        }

      case HttpResponse(statusCode, _, entity, _) =>
        Unmarshal(entity).to[String].map(s => Right((statusCode, s)))
    }

  class richDate(header : Optional[Date]) {
    def optionInstant = header.asScala.map(_.date).map(d => new Instant(d.clicks))
  }
  implicit def akkaDateHeaderToInstant(header : Optional[Date]) = new richDate(header)

  def renewTokens(tokens : GoogleTokens) : Future[Option[GoogleTokens]] = {
    import HttpMethods._

    if (tokens.refresh_token.isDefined) {
      if (tokens.isPastHalfLife) {
        for {
          response <- http.singleRequest(
            HttpRequest(
              method = POST,
              uri = config.oauthTokenUri,
              entity = FormData(
                "client_id" -> config.clientId,
                "client_secret" -> config.clientSecret,
                "refresh_token" -> tokens.refresh_token.get,
                "grant_type" -> "refresh_token"
              ).toEntity
            )
          )
          tokensOrError <- unmarshal[GoogleTokens](response, googleTokensFormat)
        } yield {
          tokensOrError match {
            case Left(newTokens) =>
              Some(
                tokens.copy(
                  access_token = newTokens.access_token,
                  expires_in = newTokens.expires_in,
                  expires_on = response.getHeader(classOf[Date]).optionInstant
                )
              )

            case Right(_) =>
              // Something more elaborate could be done, but a None will trigger a user re-authorization which will
              // reveal the real problem or fix it.
              None
          }
        }
      }
      else {
        FastFuture(Success(Some(tokens)))
      }
    }
    else {
      FastFuture(Success(Option.empty[GoogleTokens]))
    }
  }
}

