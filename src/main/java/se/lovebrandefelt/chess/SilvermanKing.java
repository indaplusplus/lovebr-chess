package se.lovebrandefelt.chess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SilvermanKing extends King {

  public SilvermanKing(Color color) {
    super(color);
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

  @Override
  public Map<Pos, Move> recursionSafeLegalMoves() {
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
