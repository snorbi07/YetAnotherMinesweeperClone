package com.norbertsram.yamc.model

import utest._
import utest.framework.TestSuite


object BoardTest extends TestSuite {
  
  /*
    Board layout:  
    M, M, E
    E, E, E
    E, E, E,
    
    Where 'M' stands for mine, and 'E' stands for empty
   */
  val testBoardData = Map(
    Coordinate(0, 0) -> Unturned(true),
    Coordinate(0, 1) -> Unturned(true),
    Coordinate(0, 2) -> Unturned(false),
    
    Coordinate(1, 0) -> Unturned(false),
    Coordinate(1, 1) -> Unturned(false),
    Coordinate(1, 2) -> Unturned(false),

    Coordinate(2, 0) -> Unturned(false),
    Coordinate(2, 1) -> Unturned(false),
    Coordinate(2, 2) -> Unturned(false)
  )
  
  def cellGenerator(coordinate: Coordinate) = testBoardData.get(coordinate).get
  
  val testBoard = new Board(3, 3, Some(cellGenerator))
  
  def tests = TestSuite {
    'turnedCellShouldBeEmpty {
      val turnCoordinate = Coordinate(2, 2)
      testBoard.turn(turnCoordinate)
      val turnedCell: CellType = testBoard.getCell(turnCoordinate)
      assert(turnedCell == Empty)
    }
    'turnedCellShouldBeNeighbouring {
      val turnCoordinate = Coordinate(1, 1)
      testBoard.turn(turnCoordinate)
      val turnedCell: CellType = testBoard.getCell(turnCoordinate)
      print(turnedCell)
      assert(turnedCell == Empty)
    }
  }
  
}
