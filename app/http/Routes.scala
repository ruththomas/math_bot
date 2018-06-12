package http

import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ StatusCodes, Uri }
import com.google.inject.Inject
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import dataentry.utility.SecureIdentifier
import utils.WebClientConfig

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class Routes @Inject()(
                        implicit val system : ActorSystem,
                        config              : WebClientConfig,
                        googleOAuth         : ActorRef
                      ) {

  implicit def ec = system.dispatcher
  implicit def materializer = ActorMaterializer()


  lazy val routes : Route = path("ping") {
    get {
      complete("pong")
    }
  } ~       path("authorized") {
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
