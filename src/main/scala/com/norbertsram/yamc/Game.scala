package com.norbertsram.yamc

import com.norbertsram.yamc.model.{Board, Cell, Coordinate, Unturned}

// Mutable state of the game
class Game(numberOfColumns: Int, numberOfRows: Int) {

  private var _board: Board = new Board(newGame(numberOfColumns, numberOfRows))

  def board = _board
  
  def board_= (board: Board) : Unit = _board = board
  
  def turn(coordinate: Coordinate): Board = {
    board = board.turn(coordinate)
    board
  }
  
  private def newGame(numberOfColumns: Int, numberOfRows: Int) : Map[Coordinate, Cell] = {
    val coordinates = for (i <- 0 until numberOfColumns; j <- 0 until numberOfRows) yield Coordinate(i, j)
    coordinates.foldLeft(Map.empty[Coordinate, Cell])(addRandomCell)
  }

  private def addRandomCell(board: Map[Coordinate, Cell], coordinate: Coordinate) : Map[Coordinate, Cell] = {
    val generator: (Coordinate) => Cell = randomCellGenerator
    board + (coordinate -> generator(coordinate))
  }

  private def randomCellGenerator(coordinate: Coordinate): Cell = {
    val difficulty = 0.10 // easy or something... this is where you would vary values is you want to change the difficulty
    def hasMineStream = math.random < difficulty
    Unturned(hasMineStream)
  }
  
}
