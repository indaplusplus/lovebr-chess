package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
  public Rook(Color color) {
    super(color, 'R');
  }

  @Override
  public Set<Move> legalMoves() {
    Set<Move> legalMoves = new HashSet<>();
    addMovesInDirection(new Pos(-1, 0), legalMoves, Move::new);
    addMovesInDirection(new Pos(1, 0), legalMoves, Move::new);
    addMovesInDirection(new Pos(0, -1), legalMoves, Move::new);
    addMovesInDirection(new Pos(0, 1), legalMoves, Move::new);
    return legalMoves;
  }
}
