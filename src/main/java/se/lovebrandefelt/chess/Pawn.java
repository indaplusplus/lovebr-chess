package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.MovementFlag.IF_EMPTY;
import static se.lovebrandefelt.chess.Piece.MovementFlag.IF_ENEMY;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
  public Pawn(Color color) {
    super(color, 'P');
  }

  @Override
  public Set<Pos> legalMoves() {
    Set<Pos> legalMoves = new HashSet<>();
    switch (getColor()) {
      case WHITE:
        addPosition(getPos().offset(1, 0), IF_EMPTY, legalMoves);
        addPosition(getPos().offset(1, -1), IF_ENEMY, legalMoves);
        addPosition(getPos().offset(1, 1), IF_ENEMY, legalMoves);
        break;
      case BLACK:
        addPosition(getPos().offset(-1, 0), IF_EMPTY, legalMoves);
        addPosition(getPos().offset(-1, -1), IF_ENEMY, legalMoves);
        addPosition(getPos().offset(-1, 1), IF_ENEMY, legalMoves);
        break;
      default:
    }
    return legalMoves;
  }
}
