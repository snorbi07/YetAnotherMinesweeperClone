package com.norbertsram.yamc

import com.norbertsram.yamc.model._

// Mutable state of the game
class Game(numberOfColumns: Int, numberOfRows: Int) {

  private var _board: Board = new Board(newGame(numberOfColumns, numberOfRows))

  def board = _board
  
  def board_= (board: Board) : Unit = _board = board
  
  def turn(coordinate: Coordinate): Board = {
    board = board.turn(coordinate)
    board
  }
  
  private def newGame(numberOfColumns: Int, numberOfRows: Int) : Map[Coordinate, CellType] = {
    val coordinates = for (i <- 0 until numberOfColumns; j <- 0 until numberOfRows) yield Coordinate(i, j)
    coordinates.foldLeft(Map.empty[Coordinate, CellType])(addRandomCell)
  }

  private def addRandomCell(board: Map[Coordinate, CellType], coordinate: Coordinate) : Map[Coordinate, CellType] = {
    val generator: (Coordinate) => CellType = randomCellGenerator
    board + (coordinate -> generator(coordinate))
  }

  private def randomCellGenerator(coordinate: Coordinate): CellType = {
    val difficulty = 0.10 // easy or something... this is where you would vary values is you want to change the difficulty
    def hasMine = math.random < difficulty
    if (hasMine) UnturnedMine else UnturnedEmpty
  }
  
}
