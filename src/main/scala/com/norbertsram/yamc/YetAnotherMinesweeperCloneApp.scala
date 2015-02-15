package com.norbertsram.yamc

import com.norbertsram.yamc.model._
import org.scalajs.dom
import org.scalajs.dom.document

import scala.scalajs.js.{Dynamic, JSON, JSApp}
import scala.scalajs.js.annotation.JSExport

object YetAnotherMinesweeperCloneApp extends JSApp {

  def main(): Unit = {
    val gameContent = document.getElementById("gameContent")
    val renderer = new DomBasedRenderer(gameContent)
    val board: Board = new Board(8, 8)
    renderer.renderBoard(board)
  }
  
  @JSExport
  def clicked(event: dom.MouseEvent): Unit = {
    event.preventDefault()
    event.stopPropagation()
    val coordinateJson = event.srcElement.getAttribute("data-coordinate")
    val json = JSON.parse(coordinateJson)
    val row = json.row.asInstanceOf[Int]
    val column = json.column.asInstanceOf[Int]
    println(s"Clicked cell: ($row,$column)")
  }
  
}