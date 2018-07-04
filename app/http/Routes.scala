package http

import actors.authFlow.AuthWebSocketFlow
import actors.messages.ProvideGoogleApiCode
import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ StatusCodes, Uri }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.google.inject.Inject
import configuration.WebClientConfig
import daos.SessionDAO
import dataentry.caches.KeyValueCache
import models.JwtToken
import utils.SecureIdentifier

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContextExecutor }


class Routes @Inject()(
                        implicit val system : ActorSystem,
                        config              : WebClientConfig,
                        googleOAuth         : ActorRef,
                        sessionDAO: SessionDAO,
                        sessionCache : KeyValueCache[SecureIdentifier, Option[JwtToken]]
                      ) {

  implicit def ec : ExecutionContextExecutor = system.dispatcher
  implicit def materializer : ActorMaterializer = ActorMaterializer()


  lazy val routes : Route =
    path("authSocket") {
      handleWebSocketMessages(
        AuthWebSocketFlow(a => {}, sessionDAO, sessionCache )
      )
    } ~ path("ping") {
      get {
        complete("pong")
      }
    } ~ path("authorized") {
    get {
      parameter('error) {
        error =>
          redirect(config.signinFail.withQuery(Uri.Query("error" -> error)), redirectionType = StatusCodes.SeeOther)

      } ~
        parameter('code, 'state) {
          (code, state) =>
            SecureIdentifier.fromStringOpt(state) match {
              case Some(sessionId) =>
                googleOAuth ! ProvideGoogleApiCode(sessionId, code)
                redirect(config.signinSuccess, StatusCodes.SeeOther)
              case _ =>
                redirect(config.signinFail.withQuery(Uri.Query("error" -> "An insecure identifier was provided with request. This could be a bug or a CSRF attack.")), redirectionType = StatusCodes.SeeOther)
            }
        }
    }
  }


  Http().bindAndHandle(handler = routes, interface = "0.0.0.0", port = 9001)

  Await.result(system.whenTerminated, Duration.Inf)

  // https://mathbot/authorize -> http://localhost:9001/authorize
}
