package se.lovebrandefelt.chess;

import javafx.geometry.Pos;

public class Board {
  private Piece[][] squares;

  public Board(int rows, int columns) {
    this.squares = new Piece[rows][columns];
  }

  public int rows() {
    return squares.length;
  }

  public int columns() {
    return squares[0].length;
  }

  public boolean isInsideBounds(Position position) {
    return position.getRow() >= 0
        && position.getRow() < rows()
        && position.getColumn() >= 0
        && position.getColumn() < columns();
  }

  public boolean isEmpty(Position position) {
    return squares[position.getRow()][position.getColumn()] == null;
  }

  public Piece get(Position position) {
    return squares[position.getRow()][position.getColumn()];
  }

  public <T extends Piece> T add(T piece, Position position) {
    squares[position.getRow()][position.getColumn()] = piece;
    piece.setBoard(this);
    piece.setPosition(position);
    return piece;
  }
}
