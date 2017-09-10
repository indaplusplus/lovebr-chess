package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {
  public Queen(Color color) {
    super(color);
  }

  @Override
  public Set<Pos> validMoves() {
    Set<Pos> validMoves = new HashSet<>();
    addPositionsInDirection(-1, -1, validMoves);
    addPositionsInDirection(-1, 1, validMoves);
    addPositionsInDirection(1, -1, validMoves);
    addPositionsInDirection(1, 1, validMoves);
    addPositionsInDirection(0, -1, validMoves);
    addPositionsInDirection(0, 1, validMoves);
    addPositionsInDirection(-1, 0, validMoves);
    addPositionsInDirection(1, 0, validMoves);
    return validMoves;
  }
}
