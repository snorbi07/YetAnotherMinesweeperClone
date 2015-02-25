package com.norbertsram.yamc.model

class Cell(val coordinate: Coordinate, val cellType: CellType) {
  
  def withType(newCellType: CellType) = new Cell(coordinate, newCellType)
  
}
