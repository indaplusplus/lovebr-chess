package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
  public Rook(Color color) {
    super(color);
  }

  @Override
  public Set<Position> validMoves() {
    Set<Position> validMoves = new HashSet<>();
    addPositionsInDirection(0, -1, validMoves);
    addPositionsInDirection(0, 1, validMoves);
    addPositionsInDirection(-1, 0, validMoves);
    addPositionsInDirection(1, 0, validMoves);
    return validMoves;
  }
}
