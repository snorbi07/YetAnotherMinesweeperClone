package com.norbertsram.yamc.model


case class Board(cells: Map[Coordinate, Cell]) {
  
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
  
  def updateCell(coordinate: Coordinate, cell: Cell) : Board = Board(cells.updated(coordinate, cell))
  
  def turn(coordinate: Coordinate) : Board = {

    def turnCell(cell: Cell) : Cell = {      
      lazy val threat = threatValue(cell)

      cell.cellType match {
        case UnturnedMine => cell.withType(Mine)
        case UnturnedEmpty => if (threat > 0)  cell.withType(Neighbouring(threat)) else cell.withType(Empty);
        case _ => cell
      }
    }
    
    val cell = getCell(coordinate)
    val newCellState = turnCell(cell)
    val updatedBoard = Board(cells.updated(coordinate, newCellState))
    val canBeTurned = !neighbourCells(cell).filter( c => c.cellType == UnturnedEmpty && threatValue(c) == 0).isEmpty
    if (canBeTurned && newCellState.cellType != Mine)
      revealNeighbours(cell, updatedBoard)
    else
      updatedBoard
  }

  def revealNeighbours(cell: Cell, board: Board) : Board = {
    val emptyNeighbours = neighbourCells(cell).filter(_.cellType == UnturnedEmpty)
    emptyNeighbours.foldLeft(board)((acc, c) => acc.turn(c.coordinate))
  }

  def threatValue(cell: Cell) =
    neighbourCells(cell).filter(_.cellType != Empty).foldLeft(0)((acc, cell) => if (cell.cellType == UnturnedMine) acc + 1 else acc)


  def neighbourCells(cell: Cell) = {
    def neighbourCoordinates(coordinate: Coordinate): List[Coordinate] = {
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

    neighbourCoordinates(cell.coordinate).map(getCell)
  } 
    
}
