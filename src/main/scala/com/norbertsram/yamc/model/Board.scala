package com.norbertsram.yamc.model


class Board(width: Int, height: Int, cellGenerator: (Coordinate => Cell)) {

  def this(width: Int, height: Int) = this(width, height, randomCell)

  private val difficulty = 0.10 // easy or something... this is where you would vary values is you want to change the difficulty

  private val cells: Map[Coordinate, Cell] = populate()
    
  private def populate() : Map[Coordinate, Cell] = {
    val coordinates = for (i <- 0 until height; j <- 0 until width) yield Coordinate(i, j)
    coordinates.foldLeft(Map.empty[Coordinate, Cell])(addRandomCell)
  }

  private def hasMineStream = math.random < difficulty

  private def addRandomCell(board: Map[Coordinate, Cell], coordinate: Coordinate) = board + (coordinate -> cellGenerator(coordinate))

  private def randomCell(coordinate: Coordinate) = Unturned(hasMineStream);

}
