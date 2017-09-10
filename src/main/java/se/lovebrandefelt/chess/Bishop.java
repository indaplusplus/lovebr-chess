package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {
  public Bishop(Color color) {
    super(color, 'B');
  }

  @Override
  public Set<Pos> validMoves() {
    Set<Pos> validMoves = new HashSet<>();
    addPositionsInDirection(-1, -1, validMoves);
    addPositionsInDirection(-1, 1, validMoves);
    addPositionsInDirection(1, -1, validMoves);
    addPositionsInDirection(1, 1, validMoves);
    return validMoves;
  }
}
