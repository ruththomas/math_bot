package dataentry.utility

import java.security.SecureRandom
import java.util.Base64

import spray.json.{ JsString, JsValue, JsonFormat }

class SecureIdentifier private (aId : Seq[Byte]) {
  private val id : Seq[Byte] = aId

  override def toString = SecureIdentifier.encoder.encodeToString(id.to[Array])

  override def equals(obj : scala.Any) = super.equals(obj) || (obj match {
    case that : SecureIdentifier => that.id.equals(this.id)
    case _ => false
  })

  override def hashCode() = this.id.hashCode()
}

object SecureIdentifier {

  private val encoder = Base64.getUrlEncoder
  private val decoder = Base64.getUrlDecoder
  private val random = new SecureRandom

  implicit object SecureIdentifierFormat extends JsonFormat[SecureIdentifier] {
    override def read(json : JsValue) = json match {
      case JsString(base64String) => SecureIdentifier(base64String)
      case _ => SecureIdentifier(0) // Effectively a worthless identifier
    }

    override def write(obj : SecureIdentifier) = JsString(obj.toString)
  }

  def apply(aId : String) : SecureIdentifier = new SecureIdentifier(decoder.decode(aId).toSeq)

  def apply(size : Int) : SecureIdentifier = {
    val buffer = Array.fill[Byte](size)(0)
    random.nextBytes(buffer)
    new SecureIdentifier(buffer.toSeq)
  }

  def empty = SecureIdentifier(0)

  def fromStringOpt(sessionId : String) : Option[SecureIdentifier] = {
    try {
      Some(SecureIdentifier(sessionId))
    }
    catch {
      case _ : IllegalArgumentException =>
        None
    }
  }

}
