package com.norbertsram.yamc

import com.norbertsram.yamc.model._
import org.scalajs.dom.document

import scala.scalajs.js.JSApp

object YetAnotherMinesweeperCloneApp extends JSApp {

  def main(): Unit = {

    val gameContent = document.getElementById("gameContent")
    val renderer = new DomBasedRenderer(gameContent)
    val board: Board = new Board(10, 10)
    val cells: Map[(Int, Int), Cell] = board.cells
    renderer.renderBoard(12, 12)
  }
  
}