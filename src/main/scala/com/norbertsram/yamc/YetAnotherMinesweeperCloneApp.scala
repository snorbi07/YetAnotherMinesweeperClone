package com.norbertsram.yamc

import org.scalajs.dom.document

import scala.scalajs.js.JSApp

object YetAnotherMinesweeperCloneApp extends JSApp {

  def main(): Unit = {

    val gameContent = document.getElementById("gameContent")
    val renderer = new DomBasedRenderer(gameContent)
    
    renderer.renderBoard(12, 12)
  }
  
}