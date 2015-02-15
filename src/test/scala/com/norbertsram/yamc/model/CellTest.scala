package com.norbertsram.yamc.model

import utest._

object CellTest extends TestSuite {

  def tests = TestSuite {
    'emptyCellState {
      assert(Empty.hasMine == false)
      assert(Empty.isTurned == true)
    }
    'turnUnturned {
      val unturnedEmptyCell = Unturned(false)
      val emptyCell = Cell.turn(unturnedEmptyCell)
      assert(emptyCell == Empty)
    }
  }

}

