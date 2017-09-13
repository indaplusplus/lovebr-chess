package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {
  public Bishop(Color color) {
    super(color, 'B');
  }

  @Override
  public Set<Move> legalMoves() {
    Set<Move> legalMoves = new HashSet<>();
    addMovesInDirection(new Pos(-1, -1), legalMoves, Move::new);
    addMovesInDirection(new Pos(-1, 1), legalMoves, Move::new);
    addMovesInDirection(new Pos(1, -1), legalMoves, Move::new);
    addMovesInDirection(new Pos(1, 1), legalMoves, Move::new);
    return legalMoves;
  }
}
