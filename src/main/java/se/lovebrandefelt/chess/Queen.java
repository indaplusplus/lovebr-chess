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
    addMovesInDirectionIfLegal(new Pos(-1, -1), legalMoves);
    addMovesInDirectionIfLegal(new Pos(-1, 1), legalMoves);
    addMovesInDirectionIfLegal(new Pos(1, -1), legalMoves);
    addMovesInDirectionIfLegal(new Pos(1, 1), legalMoves);
    addMovesInDirectionIfLegal(new Pos(-1, 0), legalMoves);
    addMovesInDirectionIfLegal(new Pos(1, 0), legalMoves);
    addMovesInDirectionIfLegal(new Pos(0, -1), legalMoves);
    addMovesInDirectionIfLegal(new Pos(0, 1), legalMoves);
    return legalMoves;
  }
}
