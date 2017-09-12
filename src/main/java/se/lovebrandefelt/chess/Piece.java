package se.lovebrandefelt.chess;

import java.util.Set;
import java.util.function.BiPredicate;

public abstract class Piece {
  public enum MovementFlag {
    IF_EMPTY,
    IF_ENEMY,
    IF_EMPTY_OR_ENEMY
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

  protected boolean addMoveIfLegal(Pos pos, MovementFlag movementFlag, Set<Pos> posSet) {
    if (!board.isInsideBounds(pos)) {
      return false;
    }
    switch (movementFlag) {
      case IF_EMPTY:
        if (!board.isEmpty(pos)) {
          return false;
        }
        break;
      case IF_ENEMY:
        if (board.isEmpty(pos) || !isEnemy(pos)) {
          return false;
        }
        break;
      case IF_EMPTY_OR_ENEMY:
        if (!board.isEmpty(pos) && !isEnemy(pos)) {
          return false;
        }
        break;
      default:
    }
    posSet.add(pos);
    return true;
  }

  protected void addMovesInDirectionIfLegal(Pos direction, Set<Pos> posSet) {
    Pos pos = this.pos.offset(direction);
    while (board.isInsideBounds(pos)) {
      if (!board.isEmpty(pos)) {
        if (isEnemy(pos)) {
          posSet.add(pos);
        }
        break;
      }
      posSet.add(pos);
      pos = pos.offset(direction);
    }
  }

  protected void addMovesInDirection(
      Pos direction, int maxMoves, boolean canCapture, Set<Pos> posSet) {
    Pos pos = this.pos.offset(direction);
    for (int i = 0; i < maxMoves && board.isInsideBounds(pos); i++) {
      if ( ! canCapture && !board.isEmpty(pos) ) {

      }
    }
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
