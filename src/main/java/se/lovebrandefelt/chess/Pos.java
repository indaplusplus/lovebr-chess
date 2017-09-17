package se.lovebrandefelt.chess;

public class Pos {
  private int row;
  private int col;

  public Pos(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Creates a new pos from the specified string written in chess notation.
   *
   * @param posString a string written in chess notation
   */
  public Pos(String posString) {
    if (posString.matches("[a-zA-Z]+[0-9]+")) {
      String rowString = posString.replaceFirst("[a-zA-Z]+", "");
      String colString = posString.replaceFirst("[0-9]+", "").toUpperCase();
      this.row = Integer.parseInt(rowString) - 1;
      this.col = 0;
      colString
          .chars()
          .forEach(
              (c) -> {
                col = col * 26 + c - 64;
              });
      col -= 1;
      return;
    }
    throw new IllegalArgumentException();
  }

  /**
   * Returns a string for the specified row.
   *
   * @param row the row
   * @return a string for the specified row
   */
  public static String rowToString(int row) {
    return "" + (row + 1);
  }

  /**
   * Returns a string for the specified column.
   *
   * @param col the column
   * @return a string for the specified column
   */
  public static String colToString(int col) {
    StringBuilder stringBuilder = new StringBuilder();
    while (col >= 0) {
      char nextLetter = (char) (col % 26 + 97);
      stringBuilder.insert(0, nextLetter);
      col = col / 26 - 1;
    }
    return stringBuilder.toString();
  }

  /**
   * Returns a position created by offsetting this position by the specified amount.
   *
   * @param offset the position to offset this position by
   * @return a position created by offsetting this position by the specified amount
   */
  public Pos offset(Pos offset) {
    return new Pos(this.row + offset.row, this.col + offset.col);
  }

  /**
   * Returns the direction between this position and the specified position.
   *
   * @param other the position to compare with
   * @return the direction between this position and the specified position
   */
  public Pos subtract(Pos other) {
    return new Pos(this.row - other.row, this.col - other.col);
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public String toString() {
    return colToString(col) + rowToString(row);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pos pos = (Pos) o;
    return row == pos.row && col == pos.col;
  }

  @Override
  public int hashCode() {
    int result = row;
    result = 31 * result + col;
    return result;
  }
}
