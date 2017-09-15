package se.lovebrandefelt.chess;

public class EnPassantMove extends Move {
  private Pos capturedPos;

  protected EnPassantMove(Pos from, Pos to) {
    super(from, to);
  }

  @Override
  public void perform(Board board) {
    Pawn piece = (Pawn) board.get(getFrom());
    capturedPos = getTo().offset(new Pos(-piece.moveDirection(), 0));
    setCaptured(board.remove(capturedPos));
    setPiece(board.add(piece, getTo()));
    board.remove(getFrom());
  }

  @Override
  public void undo(Board board) {
    board.add(getPiece(), getFrom());
    board.remove(getTo());
    board.add(getCaptured(), capturedPos);
  }
}
