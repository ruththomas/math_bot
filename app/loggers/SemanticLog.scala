package utils

import akka.http.scaladsl.model.{ StatusCode, Uri }

import scala.reflect.ClassTag

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
    final val classTag = 'classTag
  }

  object tags {
    def `class`[T](classTag : ClassTag[T]) : (Symbol, String) = symbols.classTag -> classTag.runtimeClass.getCanonicalName
    def actorBecomes(name : String) = Seq(symbols.actorBecomes -> name)
    def description(d : String) = Seq(symbols.description -> d)
    def exception(c : Throwable, d : String) = cause(c) ++ description(d)
    def cause(c : Throwable) = Seq(symbols.cause -> c.toString)
    def mongo(sessionId : SecureIdentifier, status : String) : Seq[(Symbol, String)] = Seq(symbols.sessionId -> sessionId.toString, symbols.mongo -> status)
    def outboundHttp(sessionId : SecureIdentifier, uri : Uri, status : StatusCode, body : String) = Seq(symbols.outboundUri -> uri.toString(), symbols.httpStatus -> status.value, symbols.httpBody -> body)
  }

  implicit class loggerExtensions(innerLog : SemanticLog) {
    def withClass[T]()(implicit classTag : ClassTag[T]) : SemanticLog = new ClassTagDecorator[T](innerLog)(classTag)
  }

  class ClassTagDecorator[T](var innerLog : SemanticLog)(implicit classTag : ClassTag[T]) extends SemanticLog {
    override def error(data : Seq[(Symbol, String)]) : Unit = innerLog.error(data :+ tags.`class`(classTag))
    override def warning(data : Seq[(Symbol, String)]) : Unit = innerLog.warning(data :+ tags.`class`(classTag))
    override def info(data : Seq[(Symbol, String)]) : Unit = innerLog.info(data :+ tags.`class`(classTag))
    override def debug(data : Seq[(Symbol, String)]) : Unit = innerLog.debug(data :+ tags.`class`(classTag))
  }

}
