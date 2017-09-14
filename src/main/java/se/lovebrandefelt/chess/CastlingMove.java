package se.lovebrandefelt.chess;

public class CastlingMove extends Move {
  private Rook rook;
  private Pos rookFrom;
  private Pos rookTo;

  protected CastlingMove(Pos from, Pos to) {
    super(from, to);
  }

  @Override
  public void perform(Board board) {
    setPiece(board.get(getFrom()));
    rookFrom = rook.getPos();
    if (getFrom().getCol() < getTo().getCol()) {
      rookTo = getTo().offset(new Pos(0, -1));
    } else {
      rookTo = getTo().offset(new Pos(0, 1));
    }
    board.remove(getFrom());
    board.remove(rookFrom);
    board.add(getPiece(), getTo());
    board.add(rook, rookTo);
  }

  @Override
  public void undo(Board board) {
    board.remove(getTo());
    board.remove(rookTo);
    board.add(getPiece(), getFrom());
    board.add(rook, rookFrom);
  }

  public Rook getRook() {
    return rook;
  }

  public void setRook(Rook rook) {
    this.rook = rook;
  }
}
