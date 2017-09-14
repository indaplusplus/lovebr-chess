package se.lovebrandefelt.chess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Knight extends Piece {
  public Knight(Color color) {
    super(color, 'N');
  }

  @Override
  public Map<Pos, Move> legalMoves() {
    Map<Pos, Move> legalMoves = new HashMap<>();
    Arrays.asList(
            new Pos(-2, -1),
            new Pos(-2, 1),
            new Pos(-1, -2),
            new Pos(-1, 2),
          new Pos(1, -2),
            new Pos(1, 2),
            new Pos(2, -1),
            new Pos(2, 1))
        .forEach((direction -> addMoveInDirection(direction, legalMoves)));
    return legalMoves;
  }
}
