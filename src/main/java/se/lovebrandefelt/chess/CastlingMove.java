package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Pos.colToString;

public class CastlingMove extends Move {
  private Rook rook;
  private Pos rookFrom;
  private Pos rookTo;

  CastlingMove(Pos from, Pos to) {
    super(from, to);
  }

  @Override
  protected void perform(Board board) {
    setPiece(board.get(getFrom()));
    rookFrom = rook.getPos();
    if (rookFrom.getCol() < getFrom().getCol()) {
      rookTo = getTo().offset(new Pos(0, 1));
    } else {
      rookTo = getTo().offset(new Pos(0, -1));
    }
    board.remove(getFrom());
    board.remove(rookFrom);
    board.add(getPiece(), getTo());
    board.add(rook, rookTo);
  }

  @Override
  protected void undo(Board board) {
    preUpdateAlgebraicNotation(board);
    board.remove(getTo());
    board.remove(rookTo);
    board.add(getPiece(), getFrom());
    board.add(rook, rookFrom);
    postUpdateAlgebraicNotation(board);
  }

  @Override
  protected void preUpdateAlgebraicNotation(Board board) {
    if (colToString(getTo().getCol()).equals("g")) {
      setAlgebraicNotation("O-O");
    } else {
      setAlgebraicNotation("O-O-O");
    }
  }

  Rook getRook() {
    return rook;
  }

  void setRook(Rook rook) {
    this.rook = rook;
  }
}
