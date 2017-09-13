package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

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
    return get(pos) == null;
  }

  public Piece get(Pos pos) {
    return squares[pos.getRow()][pos.getCol()];
  }

  public boolean isThreatened(Pos pos, Color by) {
    for (int row = 0; row < rows(); row++) {
      for (int col = 0; col < columns(); col++) {
        Pos currentPos = new Pos(row, col);
        if (!isEmpty(currentPos)
            && get(currentPos).getColor() == by
            && get(currentPos).legalMoves().contains(pos)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean kingInCheck(Color color) {
    for (int row = 0; row < rows(); row++) {
      for (int col = 0; col < columns(); col++) {
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
    for (int i = 0; i < columns(); i++) {
      add(new Pawn(color), new Pos(row, i));
    }
  }

  public Piece remove(Pos pos) {
    Piece piece = get(pos);
    squares[pos.getRow()][pos.getCol()] = null;
    return piece;
  }

  public void move(Pos from, Pos to) {
    history.push(new MoveEvent(remove(from), from, to, get(to)));
    add(history.peek().getPiece(), to);
  }

  public void undoMove() {
    MoveEvent move = history.pop();
    add(move.getPiece(), move.getFrom());
    if (move.getCaptured() != null) {
      add(move.getCaptured(), move.getTo());
    } else {
      remove(move.getTo());
    }
  }

  public Stack<MoveEvent> getHistory() {
    return history;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("");
    StringBuilder colStringBuilder = new StringBuilder("  ");
    for (int col = 0; col < columns(); col++) {
      colStringBuilder.append(String.format("%-2s", Pos.colToString(col)));
    }
    colStringBuilder.append("  ");
    stringBuilder.append(colStringBuilder);
    stringBuilder.append("Captures: ");
    history
        .stream()
        .filter((move) -> move.getCaptured() != null && move.getCaptured().getColor() == WHITE)
        .map(MoveEvent::getCaptured)
        .sorted()
        .forEach((capture) -> stringBuilder.append(capture.getTypeId()).append(' '));
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
        .map(MoveEvent::getCaptured)
        .sorted()
        .forEach((capture) -> stringBuilder.append(capture.getTypeId()).append(' '));
    return stringBuilder.toString();
  }
}
