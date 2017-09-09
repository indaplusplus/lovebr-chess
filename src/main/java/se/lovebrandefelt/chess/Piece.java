package se.lovebrandefelt.chess;

import java.util.Set;

public abstract class Piece {
  public enum MovementFlag {
    IF_EMPTY,
    IF_ENEMY,
    IF_EMPTY_OR_ENEMY
  }

  private Board board;
  private Position position;
  private Color color;

  protected Piece(Color color) {
    this.board = null;
    this.position = null;
    this.color = color;
  }

  protected boolean addPosition(
      Position position, MovementFlag movementFlag, Set<Position> positionSet) {
    if (!board.isInsideBounds(position)) {
      return false;
    }
    switch (movementFlag) {
      case IF_EMPTY:
        if (!board.isEmpty(position)) {
          return false;
        }
        break;
      case IF_ENEMY:
        if (board.isEmpty(position) || !isEnemy(position)) {
          return false;
        }
        break;
      case IF_EMPTY_OR_ENEMY:
        if (!board.isEmpty(position) && !isEnemy(position)) {
          return false;
        }
        break;
      default:
    }
    positionSet.add(position);
    return true;
  }

  protected void addPositionsInDirection(
      int rowOffset, int columnOffset, Set<Position> positionSet) {
    Position position = this.position.offset(rowOffset, columnOffset);
    while (board.isInsideBounds(position)) {
      if (!board.isEmpty(position)) {
        if (isEnemy(position)) {
          positionSet.add(position);
        }
        break;
      }
      positionSet.add(position);
      position = position.offset(rowOffset, columnOffset);
    }
  }

  public boolean isEnemy(Position position) {
    return !board.isEmpty(position) && board.get(position).color != color;
  }

  public abstract Set<Position> validMoves();

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
