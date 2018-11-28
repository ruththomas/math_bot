package models.compiler

import compiler.Point
import _root_.compiler.Grid
import play.api.libs.json.{ JsValue, Json, Writes }

/* Representation of a grid that is transmitted to the client as part of each frame */
case class ClientGrid(cells: Set[ClientCell])

object ClientGrid {

  implicit val gridWrites : Writes[ClientGrid] = new Writes[ClientGrid] {
    override def writes(o: ClientGrid): JsValue = Json.obj(
      "cells" -> Json.toJson(o.cells.toList)
    )
  }

  def apply(grid: Grid): ClientGrid = {
    val cells =
      grid.grid.map(g => ClientCell(Point(g._1._1, g._1._2), g._2)).toSet
    ClientGrid(cells)
  }
}