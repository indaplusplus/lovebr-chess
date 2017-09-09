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

  protected void addPosition(
      Position position, MovementFlag movementFlag, Set<Position> positionSet) {
    if (!board.isInsideBounds(position)) {
      return;
    }
    switch (movementFlag) {
      case IF_EMPTY:
        if (!board.isEmpty(position)) {
          return;
        }
        break;
      case IF_ENEMY:
        if (board.isEmpty(position) || isFriendly(position)) {
          return;
        }
        break;
      case IF_EMPTY_OR_ENEMY:
        if (!board.isEmpty(position) && isFriendly(position)) {
          return;
        }
        break;
      default:
    }
    positionSet.add(position);
  }

  public boolean isFriendly(Position position) {
    return !board.isEmpty(position) && board.get(position).color == color;
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
