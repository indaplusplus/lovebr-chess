package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.CaptureRule.CANT_CAPTURE;
import static se.lovebrandefelt.chess.Piece.CaptureRule.MUST_CAPTURE;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
  public Pawn(Color color) {
    super(color, 'P');
  }

  @Override
  public Set<Move> legalMoves() {
    Set<Move> legalMoves = new HashSet<>();
    switch (getColor()) {
      case WHITE:
        addMovesInDirection(new Pos(1, 0), legalMoves, Move::new, CANT_CAPTURE, 1);
        addMovesInDirection(new Pos(1, -1), legalMoves, Move::new, MUST_CAPTURE, 1);
        addMovesInDirection(new Pos(1, 1), legalMoves, Move::new, MUST_CAPTURE, 1);

        if (getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == this)
            && getBoard().isEmpty(getPos().offset(new Pos(1, 0)))) {
          addMovesInDirection(new Pos(2, 0), legalMoves, Move::new, CANT_CAPTURE, 1);
        }

        // Checks for available en passant moves
        if (!getBoard().getHistory().empty()) {
          Move lastMove = getBoard().getHistory().peek();
          if (lastMove.getPiece().getTypeId() == 'P'
              && lastMove.getTo().subtract(lastMove.getFrom()).equals(new Pos(-2, 0))) {
            Pos difference = lastMove.getTo().subtract(getPos());
            if (difference.equals(new Pos(0, -1)) || difference.equals(new Pos(0, 1))) {
              addMoveInDirection(new Pos(1, 0).offset(difference), legalMoves, EnPassantMove::new);
            }
          }
        }
        break;
      case BLACK:
        addMovesInDirection(new Pos(-1, 0), legalMoves, Move::new, CANT_CAPTURE, 1);
        addMovesInDirection(new Pos(-1, -1), legalMoves, Move::new, MUST_CAPTURE, 1);
        addMovesInDirection(new Pos(-1, 1), legalMoves, Move::new, MUST_CAPTURE, 1);

        if (getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == this)
            && getBoard().isEmpty(getPos().offset(new Pos(-1, 0)))) {
          addMovesInDirection(new Pos(-2, 0), legalMoves, Move::new, CANT_CAPTURE, 1);
        }

        // Checks for available en passant moves
        if (!getBoard().getHistory().empty()) {
          Move lastMove = getBoard().getHistory().peek();
          if (lastMove.getPiece().getTypeId() == 'P'
              && lastMove.getTo().subtract(lastMove.getFrom()).equals(new Pos(2, 0))) {
            Pos difference = lastMove.getTo().subtract(getPos());
            if (difference.equals(new Pos(0, -1)) || difference.equals(new Pos(0, 1))) {
              addMoveInDirection(new Pos(-1, 0).offset(difference), legalMoves, EnPassantMove::new);
            }
          }
        }
        break;
      default:
    }
    return legalMoves;
  }

  /*@Override
  public void setPos(Pos pos) {
    super.setPos(pos);

    // Captures the captured piece after doing an en passant move
    if (!getBoard().getHistory().empty()) {
      Move lastMove = getBoard().getHistory().peek();
      if (lastMove.getPiece() == this && lastMove.getCaptured() == null) {
        switch (getColor()) {
          case WHITE:
            Pos difference = lastMove.getTo().subtract(lastMove.getFrom());
            if (difference == new Pos(1, -1) || difference == new Pos(1, 1)) {
              Piece captured = getBoard().get(getPos().offset(new Pos(-1, 0)));
              //lastMove.setCaptured(captured);
              //lastMove.setTo(captured.getPos());
              getBoard().remove(captured.getPos());
            }
            break;
          case BLACK:
            difference = lastMove.getTo().subtract(lastMove.getFrom());
            if (difference.equals(new Pos(-1, -1)) || difference.equals(new Pos(-1, 1))) {
              Piece captured = getBoard().get(getPos().offset(new Pos(1, 0)));
              //lastMove.setCaptured(captured);
              //lastMove.setTo(captured.getPos());
              getBoard().remove(captured.getPos());
            }
            break;
          default:
        }
      }
    }
  }*/
}
