package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.CaptureRule.CAN_CAPTURE;

import java.util.Map;
import java.util.function.BiFunction;

public abstract class Piece {
  protected void addMovesInDirection(
      Pos direction,
      Map<Pos, Move> legalMoves,
      BiFunction<Pos, Pos, Move> moveSupplier,
      CaptureRule captureRule,
      int maxMoves) {
    captureRule.perform(direction, legalMoves, moveSupplier, maxMoves, this);
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

  protected void addMovesInDirection(Pos direction, Map<Pos, Move> legalMoves) {
    CAN_CAPTURE.perform(direction, legalMoves, Move::new, -1, this);
  }

  protected void addMoveInDirection(Pos direction, Map<Pos, Move> legalMoves) {
    CAN_CAPTURE.perform(direction, legalMoves, Move::new, 1, this);
  }

  public abstract Map<Pos, Move> legalMoves();

  public Map<Pos, Move> recursionSafeLegalMoves() {
    return legalMoves();
  }

  public boolean isEnemy(Pos pos) {
    return !board.isEmpty(pos) && board.get(pos).color != color;
  }

  public enum CaptureRule {
    CAN_CAPTURE {
      @Override
      public void perform(
          Pos direction,
          Map<Pos, Move> legalMoves,
          BiFunction<Pos, Pos, Move> moveSupplier,
          int maxMoves,
          Piece piece) {
        Pos to = piece.pos.offset(direction);
        if (maxMoves < 0) {
          while (piece.board.isInsideBounds(to)) {
            if (!piece.board.isEmpty(to)) {
              if (piece.isEnemy(to)) {
                legalMoves.put(to, moveSupplier.apply(piece.pos, to));
              }
              break;
            }
            legalMoves.put(to, moveSupplier.apply(piece.pos, to));
            to = to.offset(direction);
          }
        } else {
          for (int i = 0; i < maxMoves && piece.board.isInsideBounds(to); i++) {
            if (!piece.board.isEmpty(to)) {
              if (piece.isEnemy(to)) {
                legalMoves.put(to, moveSupplier.apply(piece.pos, to));
              }
              break;
            }
            legalMoves.put(to, moveSupplier.apply(piece.pos, to));
            to = to.offset(direction);
          }
        }
      }
    },
    CANT_CAPTURE {
      @Override
      public void perform(
          Pos direction,
          Map<Pos, Move> legalMoves,
          BiFunction<Pos, Pos, Move> moveSupplier,
          int maxMoves,
          Piece piece) {
        Pos to = piece.pos.offset(direction);
        if (maxMoves < 0) {
          while (piece.board.isInsideBounds(to) && piece.board.isEmpty(to)) {
            legalMoves.put(to, moveSupplier.apply(piece.pos, to));
            to = to.offset(direction);
          }
        } else {
          for (int i = 0;
               i < maxMoves && piece.board.isInsideBounds(to) && piece.board.isEmpty(to);
               i++) {
            legalMoves.put(to, moveSupplier.apply(piece.pos, to));
            to = to.offset(direction);
          }
        }
      }
    },
    MUST_CAPTURE {
      @Override
      public void perform(
          Pos direction,
          Map<Pos, Move> legalMoves,
          BiFunction<Pos, Pos, Move> moveSupplier,
          int maxMoves,
          Piece piece) {
        Pos to = piece.pos.offset(direction);
        if (maxMoves < 0) {
          if (piece.board.isInsideBounds(to) && !piece.board.isEmpty(to) && piece.isEnemy(to)) {
            legalMoves.put(to, moveSupplier.apply(piece.pos, to));
          }
        } else if (maxMoves > 0
            && piece.board.isInsideBounds(to)
            && !piece.board.isEmpty(to)
            && piece.isEnemy(to)) {
          legalMoves.put(to, moveSupplier.apply(piece.pos, to));
        }
      }
    };

    public abstract void perform(
        Pos direction,
        Map<Pos, Move> legalMoves,
        BiFunction<Pos, Pos, Move> moveSupplier,
        int maxMoves,
        Piece piece);
  }

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
