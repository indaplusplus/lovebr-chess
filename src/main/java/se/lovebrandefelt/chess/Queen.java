package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {
  public Queen(Color color) {
    super(color, 'Q');
  }

  @Override
  public Set<Move> legalMoves() {
    Set<Move> legalMoves = new HashSet<>();
    addMovesInDirection(new Pos(-1, -1), legalMoves, Move::new);
    addMovesInDirection(new Pos(-1, 1), legalMoves, Move::new);
    addMovesInDirection(new Pos(1, -1), legalMoves, Move::new);
    addMovesInDirection(new Pos(1, 1), legalMoves, Move::new);
    addMovesInDirection(new Pos(-1, 0), legalMoves, Move::new);
    addMovesInDirection(new Pos(1, 0), legalMoves, Move::new);
    addMovesInDirection(new Pos(0, -1), legalMoves, Move::new);
    addMovesInDirection(new Pos(0, 1), legalMoves, Move::new);
    return legalMoves;
  }
}
