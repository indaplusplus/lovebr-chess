package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.CaptureRule.CAN_CAPTURE;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class Piece {
  public enum CaptureRule {
    CAN_CAPTURE {
      @Override
      public void perform(Pos direction, Set<Move> legalMoves, BiFunction<Pos, Pos, Move> moveSupplier, int maxMoves, Piece piece) {
        Pos pos = piece.pos.offset(direction);
        if (maxMoves < 0) {
          while (piece.board.isInsideBounds(pos)) {
            if (!piece.board.isEmpty(pos)) {
              if (piece.isEnemy(pos)) {
                legalMoves.add(moveSupplier.apply(piece.pos, pos));
              }
              break;
            }
            legalMoves.add(moveSupplier.apply(piece.pos, pos));
            pos = pos.offset(direction);
          }
        } else {
          for (int i = 0; i < maxMoves && piece.board.isInsideBounds(pos); i++) {
            if (!piece.board.isEmpty(pos)) {
              if (piece.isEnemy(pos)) {
                legalMoves.add(moveSupplier.apply(piece.pos, pos));
              }
              break;
            }
            legalMoves.add(moveSupplier.apply(piece.pos, pos));
            pos = pos.offset(direction);
          }
        }
      }
    },
    CANT_CAPTURE {
      @Override
      public void perform(Pos direction, Set<Move> legalMoves, BiFunction<Pos, Pos, Move> moveSupplier, int maxMoves, Piece piece) {
        Pos pos = piece.pos.offset(direction);
        if (maxMoves < 0) {
          while (piece.board.isInsideBounds(pos) && piece.board.isEmpty(pos)) {
            legalMoves.add(moveSupplier.apply(piece.pos, pos));
            pos = pos.offset(direction);
          }
        } else {
          for (int i = 0;
              i < maxMoves && piece.board.isInsideBounds(pos) && piece.board.isEmpty(pos);
              i++) {
            legalMoves.add(moveSupplier.apply(piece.pos, pos));
            pos = pos.offset(direction);
          }
        }
      }
    },
    MUST_CAPTURE {
      @Override
      public void perform(Pos direction, Set<Move> legalMoves, BiFunction<Pos, Pos, Move> moveSupplier, int maxMoves, Piece piece) {
        Pos pos = piece.pos.offset(direction);
        if (maxMoves < 0) {
          if (piece.board.isInsideBounds(pos) && !piece.board.isEmpty(pos) && piece.isEnemy(pos)) {
            legalMoves.add(moveSupplier.apply(piece.pos, pos));
          }
        } else if (maxMoves > 0
            && piece.board.isInsideBounds(pos)
            && !piece.board.isEmpty(pos)
            && piece.isEnemy(pos)) {
          legalMoves.add(moveSupplier.apply(piece.pos, pos));
        }
      }
    };

    public abstract void perform(Pos direction, Set<Move> legalMoves, BiFunction<Pos, Pos, Move> moveSupplier, int maxMoves, Piece piece);
  }

  private final char typeId;

  private Board board;
  private Pos pos;
  private Color color;

  protected Piece(Color color, char typeId) {
    this.board = null;
    this.pos = null;
    this.color = color;
    this.typeId = typeId;
  }

  protected void addMovesInDirection(
      Pos direction,
      Set<Move> legalMoves,
      BiFunction<Pos, Pos, Move> moveSupplier,
      CaptureRule captureRule,
      int maxMoves) {
    captureRule.perform(direction, legalMoves, moveSupplier, maxMoves, this);
  }

  protected void addMovesInDirection(
      Pos direction, Set<Move> legalMoves, BiFunction<Pos, Pos, Move> moveSupplier) {
    CAN_CAPTURE.perform(direction, legalMoves, moveSupplier, -1, this);
  }

  protected void addMoveInDirection(
      Pos direction, Set<Move> legalMoves, BiFunction<Pos, Pos, Move> moveSupplier) {
    CAN_CAPTURE.perform(direction, legalMoves, moveSupplier, 1, this);
  }

  public boolean isEnemy(Pos pos) {
    return !board.isEmpty(pos) && board.get(pos).color != color;
  }

  public abstract Set<Move> legalMoves();

  public char getTypeId() {
    return typeId;
  }

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public Pos getPos() {
    return pos;
  }

  public void setPos(Pos pos) {
    this.pos = pos;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
