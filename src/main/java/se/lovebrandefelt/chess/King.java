package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.MovementFlag.IF_EMPTY_OR_ENEMY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
  public King(Color color) {
    super(color);
  }

  @Override
  public Set<Position> validMoves() {
    Set<Position> validMoves = new HashSet<>();
    Arrays.asList(
            getPosition().offset(-1, -1),
            getPosition().offset(-1, 0),
            getPosition().offset(-1, 1),
            getPosition().offset(0, -1),
            getPosition().offset(0, 0),
            getPosition().offset(0, 1),
            getPosition().offset(1, -1),
            getPosition().offset(1, 0),
            getPosition().offset(1, 1))
        .forEach((position -> addPosition(position, IF_EMPTY_OR_ENEMY, validMoves)));
    return validMoves;
  }
}
