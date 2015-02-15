package com.norbertsram.yamc.model


class Board(val numberOfRows: Int, val numberOfColumns: Int, cellGenerator: Option[Coordinate => Cell] = None) {
  // NOTE(snorbi07): ehh, there might be a more pragmatic/functional Scala way of doing this...
  private var cells: Map[Coordinate, Cell] = populate()

  private def populate() : Map[Coordinate, Cell] = {
    val coordinates = for (i <- 0 until numberOfColumns; j <- 0 until numberOfRows) yield Coordinate(i, j)
    coordinates.foldLeft(Map.empty[Coordinate, Cell])(addRandomCell)
  }

  private def addRandomCell(board: Map[Coordinate, Cell], coordinate: Coordinate) : Map[Coordinate, Cell] = {
    val generator: (Coordinate) => Cell = cellGenerator.getOrElse(randomCellGenerator)
    board + (coordinate -> generator(coordinate))
  }

  private def randomCellGenerator(coordinate: Coordinate): Cell = {
    val difficulty = 0.10 // easy or something... this is where you would vary values is you want to change the difficulty
    def hasMineStream = math.random < difficulty
    Unturned(hasMineStream)
  }
  
  /*  0 1 2 3 4 - number of columns = 4
   0  C C C C C
   1  C X C C C
   2  C C C C C
   3  C C C C C
   4  C C C C C   
   */
  
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

  def getCell(coordinate: Coordinate): Cell = {
    if (!cells.contains(coordinate)) {
      throw new IllegalArgumentException(s"${this.getClass.getSimpleName} does not contain a cell for the coordinate $coordinate")
    }
    cells.get(coordinate).get
  } 
    
  def turn(coordinate: Coordinate) = {
    val cell = getCell(coordinate)
    val neighbours = neighbourCoordinates(coordinate).map(getCell)
    val newCellState = turnCell(cell, neighbours)
    cells = cells.updated(coordinate, newCellState)
  }

  private def turnCell(cell: Cell, neighbours: List[Cell]) : Cell = {
    def newCellState() = {
      if (cell.hasMine) {
        Mine
      } 
      else {
        val threatValue: Int = neighbours.filter(_ != Empty).foldLeft(0) { (acc, cell) => if (cell.hasMine) acc + 1 else acc }
        if (threatValue == 0) Empty else Neighbouring(threatValue)
      }
    }
    cell match {
      case Unturned(hasMine) => newCellState()
      case Flagged(hasMine) => newCellState()
      case _ => throw new IllegalArgumentException(s"Cannot turn cell in state ${cell.getClass}")
    }
  }

  def flag(cell: Cell) : Cell = cell match {
    case Unturned(hasMine) => Flagged(hasMine)
    case _ => throw new IllegalArgumentException(s"Cannot flag cell in state ${cell.getClass}")
  }

  def unflag(cell: Cell) : Cell = cell match {
    case Flagged(hasMine) => Unturned(hasMine)
    case _ => throw new IllegalArgumentException(s"Cannot unflag cell in state ${cell.getClass}")
  }
  
  
}
