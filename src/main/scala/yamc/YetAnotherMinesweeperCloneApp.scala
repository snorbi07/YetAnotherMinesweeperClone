package com.norbertsram.yamc;

import scala.scalajs.js.JSApp
import org.scalajs.dom;
import dom.document;

object YetAnotherMinesweeperCloneApp extends JSApp {
  def main(): Unit = {
    writeSomething(document.body, "Hello world!")
  }

  def writeSomething(targetNode: dom.Node, text: String): Unit = {
    val pNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    pNode appendChild textNode
    targetNode appendChild pNode
  }

}