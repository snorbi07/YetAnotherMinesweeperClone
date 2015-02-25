package com.norbertsram.yamc.ui

import com.norbertsram.yamc.model._
import org.scalajs.dom
import org.scalajs.dom.document


class DomBasedRenderer(val targetNode: dom.Node) extends Renderer {

  val boardStyle = "border: 2px solid black; border-spacing: 2px;"
  val cellStyle = "border: 2px solid black; padding: 2px;"
  
  // FIXME (snorbi07): convert to functional style
  override def renderBoard(board: Board): Unit = {
    val rows = board.numberOfRows
    val columns = board.numberOfColumns

    if (targetNode == null) {
      // FIXME: handle error
      return 
    }
    
    val tableNode = document.createElement("table")
    tableNode.setAttribute("style", boardStyle)
    val tableBody = document.createElement("tbody")

    tableNode.appendChild(tableBody)

    for (row <- 0 until rows) {
      val rowNode = document.createElement("tr")
      tableBody.appendChild(rowNode)
      for (col <- 0 until columns) {
        val colNode = document.createElement("td")
        colNode.setAttribute("style", cellStyle)
        colNode.setAttribute("data-coordinate", s"""{ "row":$row, "column":$col }""")
        val cell = board.getCell(Coordinate(row, col))
        colNode.appendChild(cellRepresentation(cell.cellType))
        rowNode.appendChild(colNode)
      }
    }

    targetNode.textContent = ""
    targetNode.appendChild(tableNode)
  }
  
  def cellRepresentation(cell: CellType) = cell match {
    case UnturnedEmpty | UnturnedMine => document.createTextNode("#")
    case Flagged(_) => document.createTextNode("?")
    case Neighbouring(threatLevel) => document.createTextNode(threatLevel.toString) 
    case Empty => document.createTextNode(" ")
    case Mine => document.createTextNode("X")
  }
  
}
