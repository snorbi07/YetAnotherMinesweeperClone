package com.norbertsram.yamc

import org.scalajs.dom
import org.scalajs.dom.document

class DomBasedRenderer(val targetNode: dom.Node) extends Renderer {

  override def renderBoard(rows: Int, columns: Int): Unit = {
    val tableNode = document.createElement("table")
    val tableBody = document.createElement("tbody")
    tableNode.appendChild(tableBody)
    
    for (row <- 0 until rows) {
      val rowNode = document.createElement("tr")
      tableBody.appendChild(rowNode)
      for (col <- 0 until columns) {
        val colNode = document.createElement("td")
        val position = document.createTextNode(s"$row,$col")
        colNode.appendChild(position)
        rowNode.appendChild(colNode)
      }
    }
    
    targetNode.appendChild(tableNode)
  }
  
}
