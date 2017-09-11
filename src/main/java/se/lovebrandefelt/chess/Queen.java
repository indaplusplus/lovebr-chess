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
    addPositionsInDirectionToSet(-1, -1, legalMoves);
    addPositionsInDirectionToSet(-1, 1, legalMoves);
    addPositionsInDirectionToSet(1, -1, legalMoves);
    addPositionsInDirectionToSet(1, 1, legalMoves);
    addPositionsInDirectionToSet(0, -1, legalMoves);
    addPositionsInDirectionToSet(0, 1, legalMoves);
    addPositionsInDirectionToSet(-1, 0, legalMoves);
    addPositionsInDirectionToSet(1, 0, legalMoves);
    return legalMoves;
  }
}
