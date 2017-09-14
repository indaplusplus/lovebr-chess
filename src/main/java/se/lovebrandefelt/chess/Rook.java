package se.lovebrandefelt.chess;

import java.util.HashMap;
import java.util.Map;

public class Rook extends Piece {
  public Rook(Color color) {
    super(color, 'R');
  }

  @Override
  public Map<Pos, Move> legalMoves() {
    Map<Pos, Move> legalMoves = new HashMap<>();
    addMovesInDirection(new Pos(-1, 0), legalMoves);
    addMovesInDirection(new Pos(1, 0), legalMoves);
    addMovesInDirection(new Pos(0, -1), legalMoves);
    addMovesInDirection(new Pos(0, 1), legalMoves);
    return legalMoves;
  }
}
