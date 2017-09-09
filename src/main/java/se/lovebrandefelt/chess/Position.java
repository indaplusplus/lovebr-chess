package se.lovebrandefelt.chess;

public class Position {
  private int row;
  private int column;

  public Position(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public Position offset(int row, int column) {
    return new Position(this.row + row, this.column + column);
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;
    return row == position.row && column == position.column;
  }

  @Override
  public int hashCode() {
    int result = row;
    result = 31 * result + column;
    return result;
  }
}
