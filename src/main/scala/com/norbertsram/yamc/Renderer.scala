package com.norbertsram.yamc

import com.norbertsram.yamc.model.Board

trait Renderer {

  def renderBoard(board: Board) : Unit
  
}
