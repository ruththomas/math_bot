package utils

import java.io.ByteArrayInputStream
import java.security.cert.{ CertificateFactory, X509Certificate }

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.util.FastFuture
import akka.stream.ActorMaterializer
import com.google.inject.Inject
import configuration.{ Auth0Config, GoogleApiConfig }
import loggers.{ AkkaSemanticLog, SemanticLog }
import models.JwtToken
import org.joda.time.{ Duration, Instant }
import pdi.jwt.JwtAlgorithm.RS256
import pdi.jwt.{ JwtJson, JwtOptions }
import play.api.libs.json.{ JsObject, JsString, Json, OFormat }

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success, Try }


class JwtTokenParser @Inject() (
                      val config : GoogleApiConfig,
                      val auth0Config : Auth0Config,
                      implicit val system : ActorSystem
                    )  {


  private val logger = new AkkaSemanticLog(system, this.getClass).withClass[JwtTokenParser]

  private var lastLoaded = Option.empty[Instant]
  private var pemCertificates : Seq[X509Certificate] = Seq.empty[X509Certificate]


  private val http = Http(system)

  private implicit val materializer : ActorMaterializer = ActorMaterializer()
  private implicit val ec : ExecutionContext = system.dispatcher

  private def LoadCertificates =
    if (lastLoaded.forall(_.isBefore(Instant.now().minus(Duration.standardDays(1)))) | pemCertificates.isEmpty) {
      Future.sequence(Seq(config.oauthPemUrl, auth0Config.pemUrl) map {
        uri =>
        for {
          response <- http.singleRequest(
            HttpRequest(
              method = HttpMethods.GET,
              uri = uri
            )
          )
          jsonOrError <- AkkaToPlayMarshaller.unmarshalToPlayJson(response)

        } yield {
          jsonOrError match {
            case Left(Some(c)) =>
              val certs = c.asInstanceOf[JsObject]
              val cf = CertificateFactory.getInstance("X.509")

              certs.fields
                .map(_._2.asInstanceOf[JsString].value.getBytes)
                .map(new ByteArrayInputStream(_))
                .map {
                  stream =>
                    val c = cf.generateCertificate(stream).asInstanceOf[X509Certificate]
                    stream.close()
                    c
                }

            case Left(None) =>
              throw GoogleOAuthException(s"Unable to retrieve Google OAuth PEM signature because the endpoint at ${config.oauthPemUrl} did not provide valid json.")

            case Right((code, reason)) =>
              throw new GoogleOAuthException("Unable to retrieve Google OAuth PEM signature certificates", code, reason)

          }

        }
      }).map(_.flatten).map {certs =>
        pemCertificates = certs
        lastLoaded = Some(Instant.now())
        pemCertificates
      }
   }
  else {
    FastFuture.successful(pemCertificates)
  }

  implicit val jtwTokenFormatter : OFormat[JwtToken] = Json.format[JwtToken]

  def parseAndVerify(encodedToken : String) : Future[Option[JwtToken]] = {

    LoadCertificates.map { certs =>

      val publicKeys = certs.map(_.getPublicKey)
      val tokens = publicKeys.map(k => JwtJson.decodeJson(encodedToken, k, Seq(RS256))) map {
        case Success(jwtJson) => Left(jwtJson)
        case Failure(t) => Right(t)
      }

      val token = tokens.find(_.isLeft).getOrElse(tokens.head) // Get the first successful or first error


      token match {
        case Left(jwtJson) =>
          Try(jwtJson.as[JwtToken]) match {
            case Success(jwt) =>
              Some(jwt)
            case Failure(t) =>
              logger.info(SemanticLog.tags.oauth("google", "Unable to parse id token") :+ SemanticLog.tags.cause(t))
              None
          }

        case Right(t) =>
          logger.info(SemanticLog.tags.oauth("google", "Unable to verify id token") :+ SemanticLog.tags.cause(t))
          None
      }
    }
  }

  def parse(encodedToken : String) : Option[JwtToken] =
    JwtJson.decodeJson(encodedToken, JwtOptions(signature = false)).toOption.flatMap(_.asOpt[JwtToken])

}