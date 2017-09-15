package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Board {
  private Piece[][] squares;
  private Map<Color, List<Piece>> pieces;
  private Stack<Move> history;

  public Board(int rows, int columns) {
    squares = new Piece[rows][columns];
    pieces = new HashMap<>();
    pieces.put(WHITE, new ArrayList<>());
    pieces.put(BLACK, new ArrayList<>());
    history = new Stack<>();
  }

  public int rows() {
    return squares.length;
  }

  public int cols() {
    return squares[0].length;
  }

  public boolean isInsideBounds(Pos pos) {
    return pos.getRow() >= 0 && pos.getRow() < rows() && pos.getCol() >= 0 && pos.getCol() < cols();
  }

  public boolean isEmpty(Pos pos) {
    return get(pos) == null;
  }

  public Piece get(Pos pos) {
    return squares[pos.getRow()][pos.getCol()];
  }

  public boolean isThreatened(Pos pos, Color by) {
    return pieces
        .get(by)
        .stream()
        .anyMatch((piece -> piece.recursionSafeLegalMoves().containsKey(pos)));
  }

  public boolean kingInCheck(Color color) {
    return pieces
        .get(color)
        .stream()
        .anyMatch(
            (piece) -> piece.getTypeId() == 'K' && isThreatened(piece.getPos(), color.next()));
  }

  public <T extends Piece> T add(T piece, Pos pos) {
    if (!isEmpty(pos)) {
      pieces.get(get(pos).getColor()).remove(get(pos));
    }
    squares[pos.getRow()][pos.getCol()] = piece;
    piece.setBoard(this);
    piece.setPos(pos);
    pieces.get(piece.getColor()).add(piece);
    return piece;
  }

  public void addPawnRow(Color color, int row) {
    for (int i = 0; i < cols(); i++) {
      add(new Pawn(color), new Pos(row, i));
    }
  }

  public Piece remove(Pos pos) {
    Piece piece = get(pos);
    if (piece != null) {
      pieces.get(piece.getColor()).remove(piece);
    }
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

  public Map<Color, List<Piece>> getPieces() {
    return pieces;
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
        .stream()
        .filter((move) -> move.getCaptured() != null && move.getCaptured().getColor() == WHITE)
        .map((move) -> move.getCaptured().getTypeId())
        .sorted()
        .forEach((typeId) -> stringBuilder.append(typeId).append(' '));
    stringBuilder.append('\n');
    for (int row = rows() - 1; row >= 0; row--) {
      String rowString = String.format("%-2s", Pos.rowToString(row));
      stringBuilder.append(rowString);
      for (Piece piece : squares[row]) {
        if (piece == null) {
          stringBuilder.append("- ");
        } else {
          if (piece.getColor() == WHITE) {
            stringBuilder.append(piece.getTypeId()).append(" ");
          } else {
            stringBuilder.append(Character.toLowerCase(piece.getTypeId())).append(" ");
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
        .map((move) -> move.getCaptured().getTypeId())
        .sorted()
        .forEach((typeId) -> stringBuilder.append(typeId).append(' '));
    return stringBuilder.toString();
  }
}
