package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
  public Rook(Color color) {
    super(color, 'R');
  }

  @Override
  public Set<Pos> legalMoves() {
    Set<Pos> legalMoves = new HashSet<>();
    addPositionsInDirection(0, -1, legalMoves);
    addPositionsInDirection(0, 1, legalMoves);
    addPositionsInDirection(-1, 0, legalMoves);
    addPositionsInDirection(1, 0, legalMoves);
    return legalMoves;
  }
}
