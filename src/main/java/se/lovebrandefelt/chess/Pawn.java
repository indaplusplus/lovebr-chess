package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
  public Pawn(Color color) {
    super(color);
  }

  @Override
  public Set<Position> validMoves() {
    Set<Position> validMoves = new HashSet<>();
    switch (getColor()) {
      case WHITE:
        addPositionIfEmpty(getPosition().offset(1, 0), validMoves);
        addPositionIfNonEmpty(getPosition().offset(1, -1), validMoves);
        addPositionIfNonEmpty(getPosition().offset(1, 1), validMoves);
        break;
      case BLACK:
        addPositionIfEmpty(getPosition().offset(-1, 0), validMoves);
        addPositionIfNonEmpty(getPosition().offset(-1, -1), validMoves);
        addPositionIfNonEmpty(getPosition().offset(-1, 1), validMoves);
        break;
      default:
    }
    return validMoves;
  }
}
