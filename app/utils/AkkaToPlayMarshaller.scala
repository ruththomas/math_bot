package utils

import akka.http.scaladsl.model.{ HttpResponse, StatusCode, StatusCodes }
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import play.api.libs.json.{ JsObject, Json }

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Try

object AkkaToPlayMarshaller {
  def unmarshalToPlayJson(response : HttpResponse)(implicit materializer : Materializer, ec : ExecutionContext) : Future[Either[Option[JsObject], (StatusCode, String)]] = {
    response match {
      case HttpResponse(StatusCodes.OK, _, entity, _) =>
        for {
          value <- Unmarshal(entity).to[String]
        } yield Left(Try(Json.parse(value).asInstanceOf[JsObject]).toOption)

      case HttpResponse(statusCode, _, entity, _) =>
        Unmarshal(entity).to[String].map(s => Right((statusCode, s"Google oauth responded with $statusCode and body $s")))
    }
  }

}
