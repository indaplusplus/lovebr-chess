package se.lovebrandefelt.chess;

public class EnPassantMove extends Move {
  protected EnPassantMove(Pos from, Pos to) {
    super(from, to);
  }

  @Override
  public void perform(Board board) {
    switch (board.get(getFrom()).getColor()) {
      case WHITE:
        setCaptured(board.remove(getTo().offset(new Pos(-1, 0))));
        break;
      case BLACK:
        setCaptured(board.remove(getTo().offset(new Pos(1, 0))));
        break;
      default:
    }
    setPiece(board.add(board.get(getFrom()), getTo()));
    board.remove(getFrom());
  }

  @Override
  public void undo(Board board) {
    board.add(getPiece(), getFrom());
    switch (board.get(getFrom()).getColor()) {
      case WHITE:
        board.add(getCaptured(), getTo().offset(new Pos(-1, 0)));
        break;
      case BLACK:
        board.add(getCaptured(), getTo().offset(new Pos(1, 0)));
        break;
      default:
    }
  }
}
