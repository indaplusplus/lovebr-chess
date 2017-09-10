package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {
  public Bishop(Color color) {
    super(color, 'B');
  }

  @Override
  public Set<Pos> legalMoves() {
    Set<Pos> legalMoves = new HashSet<>();
    addPositionsInDirection(-1, -1, legalMoves);
    addPositionsInDirection(-1, 1, legalMoves);
    addPositionsInDirection(1, -1, legalMoves);
    addPositionsInDirection(1, 1, legalMoves);
    return legalMoves;
  }
}
