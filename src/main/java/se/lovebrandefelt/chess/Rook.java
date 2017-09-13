package se.lovebrandefelt.chess;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
  public Rook(Color color) {
    super(color, 'R');
  }

  @Override
  public Set<Pos> legalMoves() {
    Set<Pos> legalMoves = new HashSet<>();
    addMovesInDirection(new Pos(-1, 0), legalMoves);
    addMovesInDirection(new Pos(1, 0), legalMoves);
    addMovesInDirection(new Pos(0, -1), legalMoves);
    addMovesInDirection(new Pos(0, 1), legalMoves);
    return legalMoves;
  }
}
