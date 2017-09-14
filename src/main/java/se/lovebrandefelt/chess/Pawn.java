package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Piece.CaptureRule.CANT_CAPTURE;
import static se.lovebrandefelt.chess.Piece.CaptureRule.CAN_CAPTURE;
import static se.lovebrandefelt.chess.Piece.CaptureRule.MUST_CAPTURE;

import java.util.HashMap;
import java.util.Map;

public class Pawn extends Piece {
  public Pawn(Color color) {
    super(color, 'P');
  }

  @Override
  public Map<Pos, Move> legalMoves() {
    Map<Pos, Move> legalMoves = new HashMap<>();
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
              Pos to = getPos().offset(new Pos(1, 0)).offset(difference);
              legalMoves.put(to, new EnPassantMove(getPos(), to));
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
              Pos to = getPos().offset(new Pos(-1, 0)).offset(difference);
              legalMoves.put(to, new EnPassantMove(getPos(), to));
            }
          }
        }
        break;
      default:
    }
    return legalMoves;
  }

  public boolean canPromote() {
    return (getPos().getRow() == getBoard().rows() - 1 && getColor() == WHITE)
        || (getPos().getRow() == 0 && getColor() == BLACK);
  }

  public void promote(Piece promoteInto) {
    getBoard().add(promoteInto, getPos());
  }
}
