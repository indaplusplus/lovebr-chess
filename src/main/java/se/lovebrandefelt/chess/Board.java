package se.lovebrandefelt.chess;

import java.util.ArrayList;
import java.util.Stack;

public class Board {
  public class MoveEvent {
    private Piece piece;
    private Pos from;
    private Pos to;
    private Piece captured;

    private MoveEvent(Piece piece, Pos from, Pos to, Piece captured) {
      this.piece = piece;
      this.from = from;
      this.to = to;
      this.captured = captured;
    }

    public Piece getPiece() {
      return piece;
    }

    public Pos getFrom() {
      return from;
    }

    public Pos getTo() {
      return to;
    }

    public Piece getCaptured() {
      return captured;
    }
  }

  private Piece[][] squares;
  private Stack<MoveEvent> history;

  public Board(int rows, int columns) {
    this.squares = new Piece[rows][columns];
    this.history = new Stack<>();
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
    history.push(
        new MoveEvent(
            squares[from.getRow()][from.getCol()], from, to, squares[to.getRow()][to.getCol()]));
    add(squares[from.getRow()][from.getCol()], to);
    remove(from);
    for (int row = 0; row < rows(); row++) {
      for (int col = 0; col < columns(); col++) {
        squares[row][col].onMove(history.peek());
      }
    }
  }

  public void undoMove() {
    MoveEvent move = history.pop();
    add(move.getPiece(), move.getFrom());
    if (move.getCaptured() != null) {
      add(move.getCaptured(), move.getTo());
    }
    for (int row = 0; row < rows(); row++) {
      for (int col = 0; col < columns(); col++) {
        squares[row][col].onUndoMove(move);
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("");
    StringBuilder colStringBuilder = new StringBuilder("  ");
    for (int col = 0; col < columns(); col++) {
      colStringBuilder.append(String.format("%-2s", Pos.colToString(col)));
    }
    stringBuilder.append(colStringBuilder);
    stringBuilder.append("\n");
    for (int row = rows() - 1; row >= 0; row--) {
      String rowString = String.format("%-2s", Pos.rowToString(row));
      stringBuilder.append(rowString);
      for (Piece piece : squares[row]) {
        if (piece == null) {
          stringBuilder.append("- ");
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
      stringBuilder.append(rowString);
      stringBuilder.append("\n");
    }
    stringBuilder.append(colStringBuilder.toString().toLowerCase());
    return stringBuilder.toString();
  }
}
