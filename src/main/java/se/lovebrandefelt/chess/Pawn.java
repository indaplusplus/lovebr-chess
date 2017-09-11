package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.MovementFlag.IF_EMPTY;
import static se.lovebrandefelt.chess.Piece.MovementFlag.IF_ENEMY;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
  private boolean hasMoved;

  public Pawn(Color color) {
    super(color, 'P');
    hasMoved = false;
  }

  @Override
  public Set<Pos> legalMoves() {
    Set<Pos> legalMoves = new HashSet<>();
    switch (getColor()) {
      case WHITE:
        addPositionToSet(getPos().offset(1, 0), IF_EMPTY, legalMoves);
        addPositionToSet(getPos().offset(1, -1), IF_ENEMY, legalMoves);
        addPositionToSet(getPos().offset(1, 1), IF_ENEMY, legalMoves);
        if (!hasMoved && getBoard().isEmpty(getPos().offset(1, 0))) {
          addPositionToSet(getPos().offset(2, 0), IF_EMPTY, legalMoves);
        }
        break;
      case BLACK:
        addPositionToSet(getPos().offset(-1, 0), IF_EMPTY, legalMoves);
        addPositionToSet(getPos().offset(-1, -1), IF_ENEMY, legalMoves);
        addPositionToSet(getPos().offset(-1, 1), IF_ENEMY, legalMoves);
        if (!hasMoved && getBoard().isEmpty(getPos().offset(-1, 0))) {
          addPositionToSet(getPos().offset(-2, 0), IF_EMPTY, legalMoves);
        }
        break;
      default:
    }
    return legalMoves;
  }

  @Override
  public void onMove() {
    hasMoved = true;
  }
}
