package se.lovebrandefelt.chess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pos {
  private int row;
  private int col;

  public Pos(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public Pos(String posString) {
    if (posString.matches("[A-Z]+[0-9]+")) {
      String rowString = posString.replaceFirst("[A-Z]+", "");
      String colString = posString.replaceFirst("[0-9]+", "");
      this.row = Integer.parseInt(rowString) - 1;
      this.col = 0;
      colString.chars().forEach((c) -> {
        col = col * 26 + c - 64;
      });
      col -= 1;
      return;
    }
    throw new IllegalArgumentException();
  }

  public Pos offset(int row, int column) {
    return new Pos(this.row + row, this.col + column);
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public String rowToString() {
    return "" + (row + 1);
  }

  public String colToString() {
    StringBuilder stringBuilder = new StringBuilder();
    int col = this.col;
    while (col > 0) {
      char nextLetter = (char) (col % 26 + 65);
      stringBuilder.insert(0, nextLetter);
      col = col / 26 - 1;
    }
    return stringBuilder.toString();
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
