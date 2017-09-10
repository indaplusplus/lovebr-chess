package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.MovementFlag.IF_EMPTY_OR_ENEMY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
  public Knight(Color color) {
    super(color, 'N');
  }

  @Override
  public Set<Pos> legalMoves() {
    Set<Pos> legalMoves = new HashSet<>();
    Arrays.asList(
            getPos().offset(-2, -1),
            getPos().offset(-2, 1),
            getPos().offset(-1, -2),
            getPos().offset(-1, 2),
            getPos().offset(1, -2),
            getPos().offset(1, 2),
            getPos().offset(2, -1),
            getPos().offset(2, 1))
        .forEach((position -> addPosition(position, IF_EMPTY_OR_ENEMY, legalMoves)));
    return legalMoves;
  }
}
