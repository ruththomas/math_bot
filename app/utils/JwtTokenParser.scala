package utils

import java.io.ByteArrayInputStream
import java.security.cert.{ CertificateFactory, X509Certificate }

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import com.google.inject.Inject
import configuration.GoogleApiConfig
import loggers.{ AkkaSemanticLog, SemanticLog }
import models.JwtToken
import org.joda.time.{ Duration, Instant }
import pdi.jwt.JwtAlgorithm.RS256
import pdi.jwt.JwtJson
import play.api.libs.json.{ Json, OFormat }

import scala.concurrent.{ Await, ExecutionContext, duration }


class JwtTokenParser @Inject() (
                      val config : GoogleApiConfig,
                      implicit val system : ActorSystem
                    )  {


  private val logger = new AkkaSemanticLog(system, this.getClass).withClass[JwtTokenParser]

  private val lastLoaded = Option.empty[Instant]

  private val http = Http(system)

  private implicit val materializer : ActorMaterializer = ActorMaterializer()
  private implicit val ec : ExecutionContext = system.dispatcher

  private def LoadCertificates() = if (lastLoaded.forall(_.isBefore(Instant.now().minus(Duration.standardDays(1)))) | pemCertificates.isEmpty) {
    Await.result( // Don't want to start without certs, so its okay to block the http call once a day
      for {
        response <- http.singleRequest(
          HttpRequest(
            method = HttpMethods.GET,
            uri = config.oauthPemUri
          )
        )
        jsonOrError <- AkkaToPlayMarshaller.unmarshalToPlayJson(response)

      } yield {
        jsonOrError match {
          case Left(Some(certs)) =>
            val cf = CertificateFactory.getInstance("X.509")

            certs.fields
              .map(_._2.asInstanceOf[String].getBytes)
              .map(new ByteArrayInputStream(_))
              .map {
                stream =>
                  val c = cf.generateCertificate(stream).asInstanceOf[X509Certificate]
                  stream.close()
                  c
              }

          case Left(None) =>
            throw GoogleOAuthException(s"Unable to retrieve Google OAuth PEM signature because the endpoint at ${config.oauthPemUri} did not provide valid json.")

          case Right((code, reason)) =>
            throw new GoogleOAuthException("Unable to retrieve Google OAuth PEM signature certificates", code , reason)

        }

      },
      duration.Duration.create(1, duration.MINUTES)
    )
  }
  else {
    pemCertificates
  }

  private var pemCertificates : Seq[X509Certificate] = Seq.empty[X509Certificate]

  implicit val jtwTokenFormatter : OFormat[JwtToken] = Json.format[JwtToken]

  def parseAndVerify(encodedToken : String) : Option[JwtToken] = {

    pemCertificates = LoadCertificates()

    val tokens = pemCertificates.map(_.getPublicKey).map(k => JwtJson.decodeJson(encodedToken, k, Seq(RS256)))
      .map(t => t.map(claim => claim.asOpt[JwtToken]))

    val token = tokens.flatMap(_.toOption).flatten.headOption
    if (token.isEmpty) { // Log the reason the token couldn't be extracted and validated
      if (tokens.exists(_.isSuccess))
        logger.info(SemanticLog.tags.oauth("google", "Unable to extract id token json"))
      else {
        logger.info(SemanticLog.tags.oauth("google", "Unable to verify id token") ++ tokens.map(t => SemanticLog.tags.cause(t.failed.get)))
      }
    }
    token
  }
}