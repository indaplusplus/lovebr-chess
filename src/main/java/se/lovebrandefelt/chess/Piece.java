package se.lovebrandefelt.chess;

import java.util.Set;

public abstract class Piece {
  private Board board;
  private Position position;
  private Color color;

  public Piece(Color color) {
    this.board = null;
    this.position = null;
    this.color = color;
  }

  void addPositionIfEmpty(Position position, Set<Position> positions) {
    if (getBoard().isInsideBounds(position) && getBoard().isEmpty(position)) {
      positions.add(position);
    }
  }

  void addPositionIfNonEmpty(Position position, Set<Position> positions) {
    if (getBoard().isInsideBounds(position) && !getBoard().isEmpty(position)) {
      positions.add(position);
    }
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
