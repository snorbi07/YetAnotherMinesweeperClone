package com.norbertsram.yamc

import org.scalajs.dom
import org.scalajs.dom.document


class DomBasedRenderer(val targetNode: dom.Node) extends Renderer {

  val boardStyle = "border: 2px solid black; border-spacing: 2px;"
  val cellStyle = "border: 2px solid black; padding: 2px;"
  
  // FIXME (snorbi07): convert to functional style
  override def renderBoard(rows: Int, columns: Int): Unit = {
    if (targetNode == null) return;
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
        val position = document.createTextNode(s"$row,$col")
        colNode.appendChild(position)
        rowNode.appendChild(colNode)
      }
    }

//    targetNode.textContent = ""
    targetNode.appendChild(tableNode)
  }
  
}
