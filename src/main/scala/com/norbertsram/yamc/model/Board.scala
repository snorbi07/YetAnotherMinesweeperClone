package com.norbertsram.yamc.model


case class Board(val cells: Map[Coordinate, CellType]) {
  
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
  
  def getCell(coordinate: Coordinate): CellType = {
    if (!cells.contains(coordinate)) {
      throw new IllegalArgumentException(s"${this.getClass.getSimpleName} does not contain a cell for the coordinate $coordinate")
    }
    cells.get(coordinate).get
  } 
  
  def turn(coordinate: Coordinate) : Board = {
    val cell = getCell(coordinate)
    val neighbours = neighbourCells(coordinate)
    val newCellState = turnCell(cell, neighbours)
    val updatedBoard = Board(cells.updated(coordinate, newCellState))
    val canBeTurned = !neighbourCells(coordinate).filter(_ == UnturnedEmpty).isEmpty
    if (canBeTurned && newCellState != Mine)
      revealNeighbours(coordinate, updatedBoard)
    else
      updatedBoard
  }

  def revealNeighbours(coordinate: Coordinate, board: Board) : Board = {
    val neighbours = neighbourCoordinates(coordinate)
    val emptyNeighbours = neighbours.filter(c => getCell(c) == UnturnedEmpty)
    emptyNeighbours.foldLeft(board)(_.turn(_))
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
  
  private def neighbourCells = neighbourCoordinates(_ : Coordinate).map(getCell)

  private def turnCell(cell: CellType, neighbours: List[CellType]) : CellType = {
    lazy val threatValue = neighbours.filter(_ != Empty).foldLeft(0) { (acc, cell) => if (cell.hasMine) acc + 1 else acc }

    cell match {
      case UnturnedMine => Mine
      case UnturnedEmpty => if (threatValue > 0) Neighbouring(threatValue) else Empty
      case _ => cell
    }
  }

}
