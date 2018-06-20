package dataentry.utility

import akka.actor.ActorSystem
import akka.event.{ LogSource, Logging, LoggingAdapter }
import spray.json.DefaultJsonProtocol._
import spray.json._

import scala.reflect.ClassTag

// By extending from akka.event.LoggingAdapter we can apply semantic logging to internal akka log hooks.
class AkkaSemanticLog[T : LogSource](system : ActorSystem, source : T)(implicit tag : ClassTag[T]) extends LoggingAdapter with SemanticLog {

  import dataentry.utility.SemanticLog.symbols

  val log = Logging(system, source)
  val sourceTag = (symbols.source, tag.runtimeClass.getSimpleName)

  case class TaggedValue(tag : Symbol, value : String)
  implicit val tvFormatter = jsonFormat2(TaggedValue)

  def asTv(data : Seq[(Symbol, String)]) = data.map(d => TaggedValue(d._1, d._2))

  override def debug(data : Seq[(Symbol, String)]) : Unit = {
    log.debug(asTv(sourceTag +: data).toJson.compactPrint)
  }

  override def info(data : Seq[(Symbol, String)]) : Unit = {
    log.info(asTv(data).toJson.compactPrint)
  }

  override def error(data : Seq[(Symbol, String)]) : Unit = {
    log.error(asTv(data).toJson.compactPrint)
  }

  override def warning(data : Seq[(Symbol, String)]) : Unit = {
    log.error(asTv(data).toJson.compactPrint)
  }

  override def isErrorEnabled = log.isErrorEnabled

  override def isWarningEnabled = log.isWarningEnabled

  override def isInfoEnabled = log.isInfoEnabled

  override def isDebugEnabled = log.isDebugEnabled

  override protected def notifyError(message : String) : Unit = this.error(SemanticLog.tags.description(message))

  override protected def notifyError(cause : Throwable, message : String) : Unit = this.error(SemanticLog.tags.cause(cause) ++ SemanticLog.tags.description(message))

  override protected def notifyWarning(message : String) : Unit = this.warning(SemanticLog.tags.description(message))

  override protected def notifyInfo(message : String) : Unit = this.info(SemanticLog.tags.description(message))

  override protected def notifyDebug(message : String) : Unit = this.debug(SemanticLog.tags.description(message))

}
