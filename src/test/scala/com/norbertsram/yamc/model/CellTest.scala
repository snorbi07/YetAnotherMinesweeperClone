package com.norbertsram.yamc.model

import utest._

object CellTest extends TestSuite {

  def tests = TestSuite {
    'emptyCellState {
      assert(Empty.hasMine == false)
      assert(Empty.isTurned == true)
    }
  }

}

