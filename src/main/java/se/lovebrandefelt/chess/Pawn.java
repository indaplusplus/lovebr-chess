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
  public Set<Pos> legalMoves() {
    Set<Pos> legalMoves = new HashSet<>();
    switch (getColor()) {
      case WHITE:
        addMovesInDirection(new Pos(1, 0), legalMoves, CANT_CAPTURE, 1);
        addMovesInDirection(new Pos(1, -1), legalMoves, MUST_CAPTURE, 1);
        addMovesInDirection(new Pos(1, 1), legalMoves, MUST_CAPTURE, 1);
        if (getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == this)
            && getBoard().isEmpty(getPos().offset(new Pos(1, 0)))) {
          addMovesInDirection(new Pos(2, 0), legalMoves, CANT_CAPTURE, 1);
        }
        break;
      case BLACK:
        addMovesInDirection(new Pos(-1, 0), legalMoves, CANT_CAPTURE, 1);
        addMovesInDirection(new Pos(-1, -1), legalMoves, MUST_CAPTURE, 1);
        addMovesInDirection(new Pos(-1, 1), legalMoves, MUST_CAPTURE, 1);
        if (getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == this)
            && getBoard().isEmpty(getPos().offset(new Pos(-1, 0)))) {
          addMovesInDirection(new Pos(-2, 0), legalMoves, CANT_CAPTURE, 1);
        }
        break;
      default:
    }
    return legalMoves;
  }
}
