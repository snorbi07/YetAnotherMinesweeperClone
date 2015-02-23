package com.norbertsram.yamc

import com.norbertsram.yamc.model.Coordinate
import com.norbertsram.yamc.ui.{Renderer, DomBasedRenderer}
import org.scalajs.dom
import org.scalajs.dom.document

import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.{JSApp, JSON}

object YetAnotherMinesweeperCloneApp extends JSApp {

  var game : Game = null
  
  var renderer : Renderer = null
  
  def main(): Unit = {
    val gameContent = document.getElementById("gameContent")
    renderer = new DomBasedRenderer(gameContent)
    game = new Game(8, 8)
    renderGame
  }
  
  def renderGame = renderer renderBoard game.board
  
  @JSExport
  def clicked(event: dom.MouseEvent): Unit = {
    event.preventDefault()
    event.stopPropagation()
    val coordinateJson = event.srcElement.getAttribute("data-coordinate")
    val json = JSON.parse(coordinateJson)
    val row = json.row.asInstanceOf[Int]
    val column = json.column.asInstanceOf[Int]
    println(s"Clicked cell: ($row,$column)")

    val coordinate: Coordinate = Coordinate(row, column)
    game.turn(coordinate)

    renderGame
  }
  
}