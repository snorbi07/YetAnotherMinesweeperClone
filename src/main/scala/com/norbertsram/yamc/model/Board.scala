package com.norbertsram.yamc.model


class Board(val cells: Map[Coordinate, Cell]) {
  
  /*  0 1 2 3 4 - number of columns = 4
   0  C C C C C
   1  C X C C C
   2  C C C C C
   3  C C C C C
   4  C C C C C   
   */
  // FIXME
  def numberOfRows = 8
  
  def numberOfColumns = 8
  
  def getCell(coordinate: Coordinate): Cell = {
    if (!cells.contains(coordinate)) {
      throw new IllegalArgumentException(s"${this.getClass.getSimpleName} does not contain a cell for the coordinate $coordinate")
    }
    cells.get(coordinate).get
  } 
    
  def turn(coordinate: Coordinate) : Board = {
    val cell = getCell(coordinate)
    val neighbours = neighbourCoordinates(coordinate).map(getCell)
    val newCellState = turnCell(cell, neighbours)



    new Board(cells.updated(coordinate, newCellState))
  }

  private def neighbourCoordinates(coordinate: Coordinate): List[Coordinate] = {
    val row = coordinate.row
    val col = coordinate.column

    val potentialNeighbours = List(
      Coordinate(row - 1, col - 1),
      Coordinate(row - 1, col),
      Coordinate(row - 1, col + 1),

      Coordinate(row, col - 1),
      //current coordinate (row, col)
      Coordinate(row, col + 1),

      Coordinate(row + 1, col - 1),
      Coordinate(row + 1, col),
      Coordinate(row + 1, col + 1)
    )

    // we might be at the edge of the table, so we need to filter the potential neighbours list
    potentialNeighbours.filter(cells.contains)
  }

  private def turnCell(cell: Cell, neighbours: List[Cell]) : Cell = {
    def newCellState() = {
      if (cell.hasMine) Mine
      else {
        val threatValue: Int = neighbours.filter(_ != Empty).foldLeft(0) { (acc, cell) => if (cell.hasMine) acc + 1 else acc }
        if (threatValue == 0) Empty else Neighbouring(threatValue)
      }
    }

    cell match {
      case Unturned(hasMine) => newCellState()
      case Flagged(hasMine) => newCellState()
      case _ => _
    }
  }

}
