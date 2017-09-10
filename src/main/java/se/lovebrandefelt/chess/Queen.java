package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {
  public Queen(Color color) {
    super(color, 'Q');
  }

  @Override
  public Set<Pos> legalMoves() {
    Set<Pos> legalMoves = new HashSet<>();
    addPositionsInDirection(-1, -1, legalMoves);
    addPositionsInDirection(-1, 1, legalMoves);
    addPositionsInDirection(1, -1, legalMoves);
    addPositionsInDirection(1, 1, legalMoves);
    addPositionsInDirection(0, -1, legalMoves);
    addPositionsInDirection(0, 1, legalMoves);
    addPositionsInDirection(-1, 0, legalMoves);
    addPositionsInDirection(1, 0, legalMoves);
    return legalMoves;
  }
}
