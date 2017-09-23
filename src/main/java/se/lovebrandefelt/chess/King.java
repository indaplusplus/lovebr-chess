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

    // Checks for castling moves
    if (!getBoard().kingInCheck(getColor())
        && getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == this)) {
      Pos to = new Pos(getPos().getRow(), 6);
      CastlingMove castlingMove = new CastlingMove(getPos(), to);
      if (getPos().getCol() < 6
          || (getPos().getCol() == 6 && getBoard().isEmpty(getPos().offset(new Pos(0, -1))))) {
        for (Pos pos = getPos().offset(new Pos(0, 1));
             getBoard().isInsideBounds(pos);
             pos = pos.offset(new Pos(0, 1))) {
          if (pos.getCol() < 7 && getBoard().isThreatened(pos, getColor().next())) {
            break;
          }
          if (!getBoard().isEmpty(pos)) {
            Piece piece = getBoard().get(pos);
            if (castlingMove.getRook() == null
                && piece.getTypeId() == 'R'
                && getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == piece)
                && piece.getColor() == getColor()) {
              castlingMove.setRook((Rook) piece);
            } else {
              break;
            }
          }
          if (pos.getCol() > 5 && castlingMove.getRook() != null) {
            legalMoves.put(to, castlingMove);
          }
        }
      }
      to = new Pos(getPos().getRow(), 2);
      castlingMove = new CastlingMove(getPos(), to);
      if (getPos().getCol() > 2
          || (getPos().getCol() == 2
          && getBoard().isEmpty(getPos().offset(new Pos(0, 1)))
          && !getBoard().isThreatened(getPos().offset(new Pos(0, 1)), getColor().next()))
          || (getPos().getCol() == 1
          && getBoard().isEmpty(getPos().offset(new Pos(0, 1)))
          && !getBoard().isThreatened(getPos().offset(new Pos(0, 1)), getColor().next())
          && getBoard().isEmpty(getPos().offset(new Pos(0, 2))))) {
        for (Pos pos = getPos().offset(new Pos(0, -1));
            getBoard().isInsideBounds(pos);
            pos = pos.offset(new Pos(0, -1))) {
          if (pos.getCol() > 1 && getBoard().isThreatened(pos, getColor().next())) {
            break;
          }
          if (!getBoard().isEmpty(pos)) {
            Piece piece = getBoard().get(pos);
            if (castlingMove.getRook() == null
                && piece.getTypeId() == 'R'
                && getBoard().getHistory().stream().noneMatch((move) -> move.getPiece() == piece)
                && piece.getColor() == getColor()) {
              castlingMove.setRook((Rook) piece);
            } else {
              break;
            }
          }
          if (pos.getCol() < 3 && castlingMove.getRook() != null) {
            legalMoves.put(to, castlingMove);
          }
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
