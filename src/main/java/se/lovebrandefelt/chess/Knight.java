package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.MovementFlag.IF_EMPTY_OR_ENEMY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
  public Knight(Color color) {
    super(color);
  }

  @Override
  public Set<Position> validMoves() {
    Set<Position> validMoves = new HashSet<>();
    Arrays.asList(
            getPosition().offset(-2, -1),
            getPosition().offset(-2, 1),
            getPosition().offset(-1, -2),
            getPosition().offset(-1, 2),
            getPosition().offset(1, -2),
            getPosition().offset(1, 2),
            getPosition().offset(2, -1),
            getPosition().offset(2, 1))
        .forEach((position -> addPosition(position, IF_EMPTY_OR_ENEMY, validMoves)));
    return validMoves;
  }
}
