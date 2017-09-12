package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.MovementFlag.IF_EMPTY_OR_ENEMY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
  public King(Color color) {
    super(color, 'K');
  }

  @Override
  public Set<Pos> legalMoves() {
    Set<Pos> legalMoves = new HashSet<>();
    Arrays.asList(
            getPos().offset(new Pos(-1, -1)),
            getPos().offset(new Pos(-1, 0)),
            getPos().offset(new Pos(-1, 1)),
            getPos().offset(new Pos(0, -1)),
            getPos().offset(new Pos(0, 1)),
            getPos().offset(new Pos(1, -1)),
            getPos().offset(new Pos(1, 0)),
            getPos().offset(new Pos(1, 1)))
        .forEach((position -> addMoveIfLegal(position, IF_EMPTY_OR_ENEMY, legalMoves)));
    return legalMoves;
  }
}
