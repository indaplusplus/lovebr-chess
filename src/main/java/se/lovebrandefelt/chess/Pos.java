package se.lovebrandefelt.chess;

public class Pos {
  private int row;
  private int col;

  public Pos(int row, int col) {
    this.row = row;
    this.col = col;
  }

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

  public static String rowToString(int row) {
    return "" + (row + 1);
  }

  public static String colToString(int col) {
    StringBuilder stringBuilder = new StringBuilder();
    while (col >= 0) {
      char nextLetter = (char) (col % 26 + 65);
      stringBuilder.insert(0, nextLetter);
      col = col / 26 - 1;
    }
    return stringBuilder.toString();
  }

  public Pos offset(Pos offset) {
    return new Pos(this.row + offset.row, this.col + offset.col);
  }

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
    return "Pos{" + "row=" + row + ", col=" + col + '}';
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
