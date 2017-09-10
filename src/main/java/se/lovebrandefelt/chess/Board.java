package se.lovebrandefelt.chess;

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

  public boolean isInsideBounds(Pos pos) {
    return pos.getRow() >= 0
        && pos.getRow() < rows()
        && pos.getCol() >= 0
        && pos.getCol() < columns();
  }

  public boolean isEmpty(Pos pos) {
    return squares[pos.getRow()][pos.getCol()] == null;
  }

  public Piece get(Pos pos) {
    return squares[pos.getRow()][pos.getCol()];
  }

  public <T extends Piece> T add(T piece, Pos pos) {
    squares[pos.getRow()][pos.getCol()] = piece;
    piece.setBoard(this);
    piece.setPos(pos);
    return piece;
  }

  public void addPawnRow(Color color, int row) {
    for (int i = 0; i < columns(); i++) {
      add(new Pawn(color), new Pos(row, i));
    }
  }

  public Piece remove(Pos pos) {
    Piece piece = squares[pos.getRow()][pos.getCol()];
    squares[pos.getRow()][pos.getCol()] = null;
    return piece;
  }

  public void move(Pos from, Pos to) {
    add(squares[from.getRow()][from.getCol()], to);
    remove(from);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("  ");
    for (int col = 0; col < columns(); col ++) {
      stringBuilder.append(String.format("%-2s", Pos.colToString(col)));
    }
    stringBuilder.append("\n");
    for (int row = rows() - 1; row >= 0; row--) {
      stringBuilder.append(String.format("%-2s", Pos.rowToString(row)));
      for (Piece piece : squares[row]) {
        if (piece == null) {
          stringBuilder.append("  ");
        } else {
          switch (piece.getColor()) {
            case WHITE:
              stringBuilder.append(piece.getTypeId()).append(" ");
              break;
            case BLACK:
              stringBuilder.append(Character.toLowerCase(piece.getTypeId())).append(" ");
              break;
            default:
          }
        }
      }
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }
}
