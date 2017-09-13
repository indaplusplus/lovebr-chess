package se.lovebrandefelt.chess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
  public King(Color color) {
    super(color, 'K');
  }

  @Override
  public Set<Move> legalMoves() {
    Set<Move> legalMoves = new HashSet<>();
    Arrays.asList(
            new Pos(-1, -1),
            new Pos(-1, 0),
            new Pos(-1, 1),
            new Pos(0, -1),
            new Pos(0, 1),
            new Pos(1, -1),
            new Pos(1, 0),
            new Pos(1, 1))
        .forEach((direction -> addMoveInDirection(direction, legalMoves, Move::new)));
    return legalMoves;
  }
}
