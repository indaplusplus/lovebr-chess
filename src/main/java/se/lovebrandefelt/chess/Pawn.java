package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Piece.CaptureRule.CANT_CAPTURE;
import static se.lovebrandefelt.chess.Piece.CaptureRule.MUST_CAPTURE;

import com.sun.org.apache.regexp.internal.RE;
import java.util.HashMap;
import java.util.Map;

public class Pawn extends Piece {
  public Pawn(Color color) {
    super(color, 'P');
  }

  @Override
  public Map<Pos, Move> legalMoves() {
    Map<Pos, Move> legalMoves = new HashMap<>();
    addMovesInDirection(new Pos(moveDirection(), 0), legalMoves, Move::new, CANT_CAPTURE, 1);
    addMovesInDirection(new Pos(moveDirection(), -1), legalMoves, Move::new, MUST_CAPTURE, 1);
    addMovesInDirection(new Pos(moveDirection(), 1), legalMoves, Move::new, MUST_CAPTURE, 1);

    if (getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == this)
        && getBoard().isEmpty(getPos().offset(new Pos(moveDirection(), 0)))) {
      addMovesInDirection(new Pos(2 * moveDirection(), 0), legalMoves, Move::new, CANT_CAPTURE, 1);
    }

    // Checks for available en passant moves
    if (!getBoard().getHistory().empty()) {
      Move lastMove = getBoard().getHistory().peek();
      if (lastMove.getPiece().getTypeId() == 'P'
          && lastMove
              .getFrom()
              .subtract(lastMove.getTo())
              .equals(new Pos(2 * moveDirection(), 0))) {
        Pos difference = lastMove.getTo().subtract(getPos());
        if (difference.equals(new Pos(0, -1)) || difference.equals(new Pos(0, 1))) {
          Pos to = getPos().offset(new Pos(moveDirection(), 0)).offset(difference);
          legalMoves.put(to, new EnPassantMove(getPos(), to));
        }
      }
    }
    return legalMoves;
  }

  public int moveDirection() {
    if (getColor() == WHITE) {
      return 1;
    } else {
      return -1;
    }
  }

  public boolean canPromote() {
    return ((getPos().getRow() + moveDirection()) % (getBoard().rows() + 1) == 8);
  }

  public void promote(Piece promoteInto) {
    getBoard().add(promoteInto, getPos());
  }
}
