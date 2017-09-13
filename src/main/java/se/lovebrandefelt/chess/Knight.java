package se.lovebrandefelt.chess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
  public Knight(Color color) {
    super(color, 'N');
  }

  @Override
  public Set<Move> legalMoves() {
    Set<Move> legalMoves = new HashSet<>();
    Arrays.asList(
            new Pos(-2, -1),
            new Pos(-2, 1),
            new Pos(-1, -2),
            new Pos(-1, 2),
          new Pos(1, -2),
            new Pos(1, 2),
            new Pos(2, -1),
            new Pos(2, 1))
        .forEach((direction -> addMoveInDirection(direction, legalMoves, Move::new)));
    return legalMoves;
  }
}
