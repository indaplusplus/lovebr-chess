package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
  public Rook(Color color) {
    super(color, 'R');
  }

  @Override
  public Set<Pos> validMoves() {
    Set<Pos> validMoves = new HashSet<>();
    addPositionsInDirection(0, -1, validMoves);
    addPositionsInDirection(0, 1, validMoves);
    addPositionsInDirection(-1, 0, validMoves);
    addPositionsInDirection(1, 0, validMoves);
    return validMoves;
  }
}
