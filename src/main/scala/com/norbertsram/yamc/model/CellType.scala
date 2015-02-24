package com.norbertsram.yamc.model

trait CellType {
  val hasMine: Boolean
  val isTurned: Boolean
}

case class Flagged(hasMine: Boolean) extends CellType {
  override val isTurned: Boolean = false
}

// FIXME(snorbi07): this is a bad name
case class Neighbouring(minesInNeighbourhood: Int) extends CellType {
  override val hasMine: Boolean = false
  override val isTurned: Boolean = true
}

case object Empty extends CellType {
  override val hasMine: Boolean = false
  override val isTurned: Boolean = true
}

case object Mine extends CellType {
  override val hasMine: Boolean = true
  override val isTurned: Boolean = true
}

case object UnturnedMine extends CellType {
  override val isTurned: Boolean = false
  override val hasMine: Boolean = true
}

case object UnturnedEmpty extends CellType {
  override val isTurned: Boolean = false
  override val hasMine: Boolean = false
}
