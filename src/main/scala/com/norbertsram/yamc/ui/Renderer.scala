package com.norbertsram.yamc.ui

import com.norbertsram.yamc.model.Board

trait Renderer {

  def renderBoard(board: Board) : Unit
  
}
