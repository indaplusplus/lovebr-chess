package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Piece.CaptureRule.CAN_CAPTURE;
import static se.lovebrandefelt.chess.Piece.CaptureRule.MUST_CAPTURE;

import java.util.Set;

public abstract class Piece {
  public enum CaptureRule {
    CAN_CAPTURE {
      @Override
      public void perform(Pos direction, Set<Pos> legalMoves, int maxMoves, Piece piece) {
        Pos pos = piece.pos.offset(direction);
        if (maxMoves < 0) {
          while (piece.board.isInsideBounds(pos)) {
            if (!piece.board.isEmpty(pos)) {
              if (piece.isEnemy(pos)) {
                legalMoves.add(pos);
              }
              break;
            }
            legalMoves.add(pos);
            pos = pos.offset(direction);
          }
        } else {
          for (int i = 0; i < maxMoves && piece.board.isInsideBounds(pos); i++) {
            if (!piece.board.isEmpty(pos)) {
              if (piece.isEnemy(pos)) {
                legalMoves.add(pos);
              }
              break;
            }
            legalMoves.add(pos);
            pos = pos.offset(direction);
          }
        }
      }
    },
    CANT_CAPTURE {
      @Override
      public void perform(Pos direction, Set<Pos> legalMoves, int maxMoves, Piece piece) {
        Pos pos = piece.pos.offset(direction);
        if (maxMoves < 0) {
          while (piece.board.isInsideBounds(pos) && piece.board.isEmpty(pos)) {
            legalMoves.add(pos);
            pos = pos.offset(direction);
          }
        } else {
          for (int i = 0; i < maxMoves && piece.board.isInsideBounds(pos) && piece.board.isEmpty(pos); i++) {
            legalMoves.add(pos);
            pos = pos.offset(direction);
          }
        }
      }
    },
    MUST_CAPTURE {
      @Override
      public void perform(Pos direction, Set<Pos> legalMoves, int maxMoves, Piece piece) {
        Pos pos = piece.pos.offset(direction);
        if (maxMoves < 0) {
          if (piece.board.isInsideBounds(pos)
              && !piece.board.isEmpty(pos)
              && piece.isEnemy(pos)) {
            legalMoves.add(pos);
          }
        } else if (maxMoves > 0
            && piece.board.isInsideBounds(pos)
            && !piece.board.isEmpty(pos)
            && piece.isEnemy(pos)) {
          legalMoves.add(pos);
        }
      }
    };

    public abstract void perform(Pos direction, Set<Pos> legalMoves, int maxMoves, Piece piece);
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
      Pos direction, Set<Pos> legalMoves, CaptureRule captureRule, int maxMoves) {
    captureRule.perform(direction, legalMoves, maxMoves, this);
  }

  protected void addMovesInDirection(Pos direction, Set<Pos> legalMoves) {
    CAN_CAPTURE.perform(direction, legalMoves, -1, this);
  }

  protected void addMoveInDirection(Pos direction, Set<Pos> legalMoves) {
    CAN_CAPTURE.perform(direction, legalMoves, 1, this);
  }

  public boolean isEnemy(Pos pos) {
    return !board.isEmpty(pos) && board.get(pos).color != color;
  }

  public abstract Set<Pos> legalMoves();

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
