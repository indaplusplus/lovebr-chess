package se.lovebrandefelt.chess;

public class EnPassantMove extends Move {
  private Pos capturedPos;

  EnPassantMove(Pos from, Pos to) {
    super(from, to);
  }

  @Override
  protected void perform(Board board) {
    Pawn piece = (Pawn) board.get(getFrom());
    capturedPos = getTo().offset(new Pos(-piece.moveDirection(), 0));
    setCaptured(board.remove(capturedPos));
    setPiece(board.add(piece, getTo()));
    board.remove(getFrom());
  }

  @Override
  protected void undo(Board board) {
    board.add(getPiece(), getFrom());
    board.remove(getTo());
    board.add(getCaptured(), capturedPos);
  }
}
