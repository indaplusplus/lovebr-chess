package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Pos.colToString;
import static se.lovebrandefelt.chess.Pos.rowToString;

import java.util.Set;
import java.util.stream.Collectors;

public class Move {
  private Piece piece;
  private Pos from;
  private Pos to;
  private Piece captured;
  private String algebraicNotation;

  protected Move(Pos from, Pos to) {
    this.from = from;
    this.to = to;
  }

  public Piece getPiece() {
    return piece;
  }

  protected void setPiece(Piece piece) {
    this.piece = piece;
  }

  public Pos getFrom() {
    return from;
  }

  public Pos getTo() {
    return to;
  }

  public Piece getCaptured() {
    return captured;
  }

  protected void setCaptured(Piece captured) {
    this.captured = captured;
  }

  /**
   * Performs this move on the specified board.
   *
   * @param board the board to perform this move on.
   */
  protected void perform(Board board) {
    piece = board.get(from);
    captured = board.get(to);
    preUpdateAlgebraicNotation(board);
    board.add(piece, to);
    board.remove(from);
    postUpdateAlgebraicNotation(board);
  }

  /**
   * Undoes this move on the specified board.
   *
   * @param board the board to undo this move on.
   */
  protected void undo(Board board) {
    board.add(piece, from);
    if (captured != null) {
      board.add(captured, to);
    } else {
      board.remove(to);
    }
  }

  protected void preUpdateAlgebraicNotation(Board board) {
    String moveString = "";
    if (getPiece().getTypeId() == 'P') {
      if (captured != null) {
        moveString += colToString(from.getCol());
      }
    } else {
      moveString += getPiece().getTypeId();
      if (board
          .getPieces()
          .get(piece.getColor())
          .stream()
          .filter(piece -> piece.getTypeId() == getPiece().getTypeId())
          .count()
          >= 2) {
        Set<Piece> others =
            board
                .getPieces()
                .get(piece.getColor())
                .stream()
                .filter(
                    piece ->
                        piece != getPiece()
                            && piece.getTypeId() == getPiece().getTypeId()
                            && piece.legalMoves().containsKey(to))
                .collect(Collectors.toSet());
        if (!others.isEmpty()) {
          if (others.stream().noneMatch(piece -> piece.getPos().getCol() == from.getCol())) {
            moveString += colToString(from.getCol());
          } else if (others.stream().noneMatch(piece -> piece.getPos().getRow() == from.getRow())) {
            moveString += rowToString(from.getRow());
          } else {
            moveString += from;
          }
        }
      }
    }
    if (captured == null) {
      moveString += to;
    } else {
      moveString += "x" + to;
    }
    algebraicNotation = moveString;
  }

  protected void postUpdateAlgebraicNotation(Board board) {
    if (board.kingInCheck(piece.getColor().next())) {
      if (board.getGame().getState() != IN_PROGRESS) {
        algebraicNotation += '#';
      } else {
        algebraicNotation += '+';
      }
    }
  }

  public String getAlgebraicNotation() {
    return algebraicNotation;
  }

  protected void setAlgebraicNotation(String algebraicNotation) {
    this.algebraicNotation = algebraicNotation;
  }

  @Override
  public String toString() {
    return algebraicNotation;
  }
}
