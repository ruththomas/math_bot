package utils

import akka.http.scaladsl.model.{StatusCode, Uri}

trait SemanticLog {

  def error(data : Seq[(Symbol, String)]) : Unit
  def warning(data : Seq[(Symbol, String)]) : Unit
  def info(data : Seq[(Symbol, String)]) : Unit
  def debug(data : Seq[(Symbol, String)]) : Unit
}

object SemanticLog {
  object symbols {
    final val mongo = 'mongo
    final val source = 'source
    final val message = 'message
    final val socketId = 'socketId
    final val contents = 'contents
    final val sessionId = 'sessionId
    final val outboundUri = 'outboundUri
    final val httpStatus = 'httpStatus
    final val httpBody = 'httpBody
    final val cause = 'cause
    final val kind = 'kind
    final val caseClass = 'caseClass
    final val description = 'description
    final val actorBecomes = 'actorBecomes
    final val invalidMessage = 'invalidMessage
  }

  object tags {
    def actorBecomes(name : String) = Seq(symbols.actorBecomes -> name)
    def description(d : String) = Seq(symbols.description -> d)
    def exception(c : Throwable, d : String) = cause(c) ++ description(d)
    def cause(c : Throwable) = Seq(symbols.cause -> c.toString)
    def mongo(sessionId : SecureIdentifier, status : String) : Seq[(Symbol, String)] = Seq(symbols.sessionId -> sessionId.toString, symbols.mongo -> status)
    def outboundHttp(sessionId : SecureIdentifier, uri : Uri, status : StatusCode, body : String) = Seq(symbols.outboundUri -> uri.toString(), symbols.httpStatus -> status.value, symbols.httpBody -> body)
  }
}
