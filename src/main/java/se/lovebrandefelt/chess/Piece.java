package se.lovebrandefelt.chess;

import java.util.Set;
import se.lovebrandefelt.chess.Board.MoveEvent;

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

  protected boolean addPositionToSet(Pos pos, MovementFlag movementFlag, Set<Pos> posSet) {
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

  protected void addPositionsInDirectionToSet(int rowOffset, int columnOffset, Set<Pos> posSet) {
    Pos pos = this.pos.offset(rowOffset, columnOffset);
    while (board.isInsideBounds(pos)) {
      if (!board.isEmpty(pos)) {
        if (isEnemy(pos)) {
          posSet.add(pos);
        }
        break;
      }
      posSet.add(pos);
      pos = pos.offset(rowOffset, columnOffset);
    }
  }

  public boolean isEnemy(Pos pos) {
    return !board.isEmpty(pos) && board.get(pos).color != color;
  }

  public abstract Set<Pos> legalMoves();

  public void onMove(MoveEvent move) {}

  public void onUndoMove(MoveEvent move) {}

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
