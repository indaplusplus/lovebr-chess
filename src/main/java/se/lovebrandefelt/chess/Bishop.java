package se.lovebrandefelt.chess;

import java.util.HashMap;
import java.util.Map;

public class Bishop extends Piece {
  public Bishop(Color color) {
    super(color, 'B');
  }

  @Override
  public Map<Pos, Move> legalMoves() {
    Map<Pos, Move> legalMoves = new HashMap<>();
    addMovesInDirection(new Pos(-1, -1), legalMoves);
    addMovesInDirection(new Pos(-1, 1), legalMoves);
    addMovesInDirection(new Pos(1, -1), legalMoves);
    addMovesInDirection(new Pos(1, 1), legalMoves);
    return legalMoves;
  }
}