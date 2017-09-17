package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.CaptureRule.CAN_CAPTURE;

import java.util.Map;
import java.util.function.BiFunction;

public abstract class Piece {
  private final char typeId;
  private Board board;
  private Pos pos;
  private Color color;

  /**
   * Creates a new piece with the specified color and the specified typeId.
   *
   * @param color the color of the piece
   * @param typeId the typeId of the piece
   */
  protected Piece(Color color, char typeId) {
    this.board = null;
    this.pos = null;
    this.color = color;
    this.typeId = typeId;
  }

  /**
   * Adds a number of moves in the specified direction to the specified map where each key is a
   * position a piece can move to and each value a corresponding Move object.
   *
   * @param direction the direction to move in
   * @param legalMoves the map of legal moves
   * @param moveSupplier the constructor for creating the moves
   * @param captureRule the capture rule to use
   * @param maxMoves the maximum number of moves to add
   */
  protected void addMovesInDirection(
      Pos direction,
      Map<Pos, Move> legalMoves,
      BiFunction<Pos, Pos, Move> moveSupplier,
      CaptureRule captureRule,
      int maxMoves) {
    captureRule.perform(direction, legalMoves, moveSupplier, maxMoves, this);
  }

  /**
   * Adds as many moves as possible in the specified direction to the specified map where each key
   * is a position a piece can move to and each value a corresponding Move object.
   *
   * @param direction the direction to move in
   * @param legalMoves the map of legal moves
   */
  protected void addMovesInDirection(Pos direction, Map<Pos, Move> legalMoves) {
    CAN_CAPTURE.perform(direction, legalMoves, Move::new, -1, this);
  }

  /**
   * Adds one move in the specified direction to the specified map where each key is a position a
   * piece can move to and each value a corresponding Move object.
   *
   * @param direction the direction to move in
   * @param legalMoves the map of legal moves
   */
  protected void addMoveInDirection(Pos direction, Map<Pos, Move> legalMoves) {
    CAN_CAPTURE.perform(direction, legalMoves, Move::new, 1, this);
  }

  /**
   * Returns a map where each key is a position this piece can move to and each value a
   * corresponding Move object.
   *
   * @return a map of legal moves
   */
  public abstract Map<Pos, Move> legalMoves();

  /**
   * A recursion safe version of legal moves to use when checking whether a position is threatened.
   *
   * @return a map of legal moves
   */
  public Map<Pos, Move> recursionSafeLegalMoves() {
    return legalMoves();
  }

  /**
   * Returns whether there is an enemy at the specified position.
   *
   * @param pos the position to check
   * @return whether there is an enemy at the specified position
   */
  public boolean isEnemy(Pos pos) {
    return !board.isEmpty(pos) && board.get(pos).color != color;
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

    /**
     * Adds a number of moves in the specified direction according to this capture rule to the
     * specified map where each key is a position a piece can move to and each value a corresponding
     * Move object.
     *
     * @param direction the direction to move in
     * @param legalMoves the map of legal moves
     * @param moveSupplier the constructor for creating the moves
     * @param maxMoves the maximum number of moves to add
     */
    public abstract void perform(
        Pos direction,
        Map<Pos, Move> legalMoves,
        BiFunction<Pos, Pos, Move> moveSupplier,
        int maxMoves,
        Piece piece);
  }
}
