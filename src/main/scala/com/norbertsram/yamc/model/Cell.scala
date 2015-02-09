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
case class Surrounding(minesInNeighbourhood: Int) extends Cell {
  override val hasMine: Boolean = false
  override val isTurned: Boolean = true
}

case object Empty extends Cell {
  override val hasMine: Boolean = false
  override val isTurned: Boolean = false
}

case object Mine extends Cell {
  override val hasMine: Boolean = true
  override val isTurned: Boolean = true
}

object Cell {
  
  def turn(cell: Cell) : Cell = cell match {
    case Unturned(hasMine) => if (hasMine) Mine else Empty // FIXME(snorbi07): it can be Surrounding as well, depends on the context
    case Flagged(hasMine) => if (hasMine) Mine else Empty
    case _ => throw new IllegalArgumentException(s"Cannot turn cell in state ${cell.getClass}")
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