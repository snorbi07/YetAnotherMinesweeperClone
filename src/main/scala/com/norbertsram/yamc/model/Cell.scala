package com.norbertsram.yamc.model

trait Cell {
  val hasMine: Boolean
  val isTurned: Boolean
}

case class Unturned(hasMine: Boolean) extends Cell {
  override val isTurned: Boolean = false
}

case class Flagged(hasMine: Boolean) extends Cell {
  override val isTurned: Boolean = false
}

// FIXME(snorbi07): this is a bad name
case class Neighbouring(minesInNeighbourhood: Int) extends Cell {
  override val hasMine: Boolean = false
  override val isTurned: Boolean = true
}

case object Empty extends Cell {
  override val hasMine: Boolean = false
  override val isTurned: Boolean = true
}

case object Mine extends Cell {
  override val hasMine: Boolean = true
  override val isTurned: Boolean = true
}
