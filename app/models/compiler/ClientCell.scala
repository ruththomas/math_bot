package models.compiler

import compiler.{ Cell, Point }
import play.api.libs.json.{ Json, OFormat }

case class ClientCell(location: Point, items: List[String])

object ClientCell {

  implicit val clientCellFormat: OFormat[ClientCell] = Json.format[ClientCell]

  def apply(loc: Point, cell: Cell): ClientCell = {
    val contents = cell.contents.map(element => element.image)
    ClientCell(loc, contents)
  }
}
