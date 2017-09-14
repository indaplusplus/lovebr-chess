package se.lovebrandefelt.chess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class King extends Piece {
  public King(Color color) {
    super(color, 'K');
  }

  @Override
  public Map<Pos, Move> legalMoves() {
    Map<Pos, Move> legalMoves = new HashMap<>();
    Arrays.asList(
            new Pos(-1, -1),
            new Pos(-1, 0),
            new Pos(-1, 1),
            new Pos(0, -1),
            new Pos(0, 1),
            new Pos(1, -1),
            new Pos(1, 0),
            new Pos(1, 1))
        .forEach((direction -> addMoveInDirection(direction, legalMoves)));
    if (!getBoard().kingInCheck(getColor())
        && getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == this)) {
      for (Pos pos = getPos().offset(new Pos(0, 1));
          getBoard().isInsideBounds(pos);
          pos = pos.offset(new Pos(0, 1))) {
        if (pos.getCol() < 7 && getBoard().isThreatened(pos, getColor().next())) {
          break;
        }
        if (!getBoard().isEmpty(pos)) {
          Piece piece = getBoard().get(pos);
          if (piece.getTypeId() == 'R'
              && getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == piece)
              && piece.getColor() == getColor()) {
            Pos to = new Pos(pos.getRow(), 6);
            CastlingMove move = new CastlingMove(getPos(), to);
            move.setRook((Rook)piece);
            legalMoves.put(to, move);
          }
          break;
        }
      }
      for (Pos pos = getPos().offset(new Pos(0, -1));
           getBoard().isInsideBounds(pos);
           pos = pos.offset(new Pos(0, -1))) {
        if (pos.getCol() > 1 && getBoard().isThreatened(pos, getColor().next())) {
          break;
        }
        if (!getBoard().isEmpty(pos)) {
          Piece piece = getBoard().get(pos);
          if (piece.getTypeId() == 'R'
              && getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == piece)
              && piece.getColor() == getColor()) {
            Pos to = new Pos(pos.getRow(), 2);
            CastlingMove move = new CastlingMove(getPos(), to);
            move.setRook((Rook)piece);
            legalMoves.put(to, move);
          }
          break;
        }
      }
    }
    return legalMoves;
  }

  @Override
  public Map<Pos, Move> recursionSafeLegalMoves() {
    Map<Pos, Move> legalMoves = new HashMap<>();
    Arrays.asList(
        new Pos(-1, -1),
        new Pos(-1, 0),
        new Pos(-1, 1),
        new Pos(0, -1),
        new Pos(0, 1),
        new Pos(1, -1),
        new Pos(1, 0),
        new Pos(1, 1))
        .forEach((direction -> addMoveInDirection(direction, legalMoves)));
    return legalMoves;
  }
}
