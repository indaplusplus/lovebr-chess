package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Stack;

public class Board {
  private Piece[][] squares;
  private Stack<Move> history;

  public Board(int rows, int columns) {
    this.squares = new Piece[rows][columns];
    this.history = new Stack<>();
  }

  public int rows() {
    return squares.length;
  }

  public int cols() {
    return squares[0].length;
  }

  public boolean isInsideBounds(Pos pos) {
    return pos.getRow() >= 0
        && pos.getRow() < rows()
        && pos.getCol() >= 0
        && pos.getCol() < cols();
  }

  public boolean isEmpty(Pos pos) {
    return get(pos) == null;
  }

  public Piece get(Pos pos) {
    return squares[pos.getRow()][pos.getCol()];
  }

  public boolean isThreatened(Pos pos, Color by) {
    for (int row = 0; row < rows(); row++) {
      for (int col = 0; col < cols(); col++) {
        Pos currentPos = new Pos(row, col);
        if (!isEmpty(currentPos)
            && get(currentPos).getColor() == by
            && get(currentPos).legalMoves().containsKey(pos)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean kingInCheck(Color color) {
    for (int row = 0; row < rows(); row++) {
      for (int col = 0; col < cols(); col++) {
        Pos currentPos = new Pos(row, col);
        if (!isEmpty(currentPos)
            && get(currentPos).getTypeId() == 'K'
            && get(currentPos).getColor() == color
            && isThreatened(currentPos, color.next())) {
          return true;
        }
      }
    }
    return false;
  }

  public <T extends Piece> T add(T piece, Pos pos) {
    squares[pos.getRow()][pos.getCol()] = piece;
    piece.setBoard(this);
    piece.setPos(pos);
    return piece;
  }

  public void addPawnRow(Color color, int row) {
    for (int i = 0; i < cols(); i++) {
      add(new Pawn(color), new Pos(row, i));
    }
  }

  public Piece remove(Pos pos) {
    Piece piece = get(pos);
    squares[pos.getRow()][pos.getCol()] = null;
    return piece;
  }

  public void move(Move move) {
    move.perform(this);
    history.push(move);
  }

  public void undoMove() {
    history.pop().undo(this);
  }

  public Stack<Move> getHistory() {
    return history;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("");
    StringBuilder colStringBuilder = new StringBuilder("  ");
    for (int col = 0; col < cols(); col++) {
      colStringBuilder.append(String.format("%-2s", Pos.colToString(col)));
    }
    colStringBuilder.append("  ");
    stringBuilder.append(colStringBuilder);
    stringBuilder.append("Captures: ");
    history
        .stream().filter((move) -> move.getCaptured() != null && move.getCaptured().getColor() == WHITE)
        .map(Move::getCaptured)
        .sorted()
        .forEach((captured) -> stringBuilder.append(captured.getTypeId()).append(' '));
    stringBuilder.append('\n');
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
      stringBuilder.append('\n');
    }
    stringBuilder.append(colStringBuilder.toString().toLowerCase());
    stringBuilder.append("Captures: ");
    history
        .stream()
        .filter((move) -> move.getCaptured() != null && move.getCaptured().getColor() == BLACK)
        .map(Move::getCaptured)
        .sorted()
        .forEach((captured) -> stringBuilder.append(captured.getTypeId()).append(' '));
    return stringBuilder.toString();
  }
}
