package utils

import akka.http.javadsl.model.StatusCode

case class GoogleOAuthException(message : String) extends Error(message) {
  def this(message : String, code : StatusCode, reason : String) = this(s"$message because of http failure ($code) => $reason")
}
