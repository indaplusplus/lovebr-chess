package se.lovebrandefelt.chess;

import java.util.HashMap;
import java.util.Map;

public class Queen extends Piece {
  public Queen(Color color) {
    super(color, 'Q');
  }

  @Override
  public Map<Pos, Move> legalMoves() {
    Map<Pos, Move> legalMoves = new HashMap<>();
    addMovesInDirection(new Pos(-1, -1), legalMoves);
    addMovesInDirection(new Pos(-1, 1), legalMoves);
    addMovesInDirection(new Pos(1, -1), legalMoves);
    addMovesInDirection(new Pos(1, 1), legalMoves);
    addMovesInDirection(new Pos(-1, 0), legalMoves);
    addMovesInDirection(new Pos(1, 0), legalMoves);
    addMovesInDirection(new Pos(0, -1), legalMoves);
    addMovesInDirection(new Pos(0, 1), legalMoves);
    return legalMoves;
  }
}
