package se.lovebrandefelt.chess;

public class EnPassantMove extends Move {
  private Pos capturedPos;

  protected EnPassantMove(Pos from, Pos to) {
    super(from, to);
  }

  @Override
  public void perform(Board board) {
    switch (board.get(getFrom()).getColor()) {
      case WHITE:
        capturedPos = getTo().offset(new Pos(-1, 0));
        setCaptured(board.remove(capturedPos));
        break;
      case BLACK:
        capturedPos = getTo().offset(new Pos(1, 0));
        setCaptured(board.remove(capturedPos));
        break;
      default:
    }
    setPiece(board.add(board.get(getFrom()), getTo()));
    board.remove(getFrom());
  }

  @Override
  public void undo(Board board) {
    board.add(getPiece(), getFrom());
    board.add(getCaptured(), capturedPos);
  }
}
