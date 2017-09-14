package se.lovebrandefelt.chess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class King extends Piece {
  public King(Color color) {
    super(color, 'K');
  }

  @Override
  public Map<Pos, Move> legalMoves() {
    Map<Pos, Move> legalMoves = new HashMap<>();
    Arrays.asList(
            new Pos(-1, -1),
            new Pos(-1, 0),
            new Pos(-1, 1),
            new Pos(0, -1),
            new Pos(0, 1),
            new Pos(1, -1),
            new Pos(1, 0),
            new Pos(1, 1))
        .forEach((direction -> addMoveInDirection(direction, legalMoves)));
    return legalMoves;
  }
}
