package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class FuncToken(
    created_id: String,
    color: String = "default",
    func: Option[List[FuncToken]],
    set: Option[Boolean] = Some(false),
    name: Option[String],
    image: Option[String],
    index: Option[Int],
    `type`: Option[String],
    commandId: Option[String],
    sizeLimit: Option[Int] = Some(-1),
    displayImage: Option[Boolean] = Some(true)
)

object FuncToken {
  val funcTokenReads: Reads[FuncToken] = (
    (JsPath \ "created_id").read[String] and
    (JsPath \ "color").read[String] and
    (JsPath \ "func").lazyReadNullable(Reads.list[FuncToken](funcTokenReads)) and
    (JsPath \ "set").readNullable[Boolean] and
    (JsPath \ "name").readNullable[String] and
    (JsPath \ "image").readNullable[String] and
    (JsPath \ "index").readNullable[Int] and
    (JsPath \ "type").readNullable[String] and
    (JsPath \ "commandId").readNullable[String] and
    (JsPath \ "sizeLimit").readNullable[Int] and
    (JsPath \ "displayImage").readNullable[Boolean]
  )(FuncToken.apply _)

  val funcTokenWrites: Writes[FuncToken] = (
    (JsPath \ "created_id").write[String] and
    (JsPath \ "color").write[String] and
    (JsPath \ "func").lazyWriteNullable(Writes.list[FuncToken](funcTokenWrites)) and
    (JsPath \ "set").writeNullable[Boolean] and
    (JsPath \ "name").writeNullable[String] and
    (JsPath \ "image").writeNullable[String] and
    (JsPath \ "index").writeNullable[Int] and
    (JsPath \ "type").writeNullable[String] and
    (JsPath \ "commandId").writeNullable[String] and
    (JsPath \ "sizeLimit").writeNullable[Int] and
    (JsPath \ "displayImage").writeNullable[Boolean]
  )(unlift(FuncToken.unapply))

  implicit val funcTokenFormat: Format[FuncToken] =
    Format(funcTokenReads, funcTokenWrites)
}
