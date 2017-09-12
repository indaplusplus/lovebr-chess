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
    Set<Pos> legalMoves = new HashSet<Pos>();
    switch (getColor()) {
      case WHITE:
        addMoveIfLegal(getPos().offset(new Pos(1, 0)), IF_EMPTY, legalMoves);
        addMoveIfLegal(getPos().offset(new Pos(1, -1)), IF_ENEMY, legalMoves);
        addMoveIfLegal(getPos().offset(new Pos(1, 1)), IF_ENEMY, legalMoves);
        if (getBoard().getHistory().parallelStream().noneMatch((move) -> move.getPiece() != this)
            && getBoard().isEmpty(getPos().offset(new Pos(1, 0)))) {
          addMoveIfLegal(getPos().offset(new Pos(2, 0)), IF_EMPTY, legalMoves);
        }
        break;
      case BLACK:
        addMoveIfLegal(getPos().offset(new Pos(-1, 0)), IF_EMPTY, legalMoves);
        addMoveIfLegal(getPos().offset(new Pos(-1, -1)), IF_ENEMY, legalMoves);
        addMoveIfLegal(getPos().offset(new Pos(-1, 1)), IF_ENEMY, legalMoves);
        if (getBoard().getHistory().parallelStream().noneMatch((move) -> move.getPiece() != this)
            && getBoard().isEmpty(getPos().offset(new Pos(-1, 0)))) {
          addMoveIfLegal(getPos().offset(new Pos(-2, 0)), IF_EMPTY, legalMoves);
        }
        break;
      default:
    }
    return legalMoves;
  }
}
