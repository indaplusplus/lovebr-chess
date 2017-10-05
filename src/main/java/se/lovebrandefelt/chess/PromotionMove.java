package se.lovebrandefelt.chess;

public class PromotionMove extends Move {
  private Character promoteInto;

  PromotionMove(Pos from, Pos to) {
    super(from, to);
  }

  @Override
  protected void perform(Board board) {
    setPiece(board.get(getFrom()));
    setCaptured(board.get(getTo()));
    preUpdateAlgebraicNotation(board);
    board.add(getPiece(), getTo());
    board.remove(getFrom());
    postUpdateAlgebraicNotation(board);
    if (promoteInto != null) {
      ((Pawn) board.get(getTo())).promoteInto(promoteInto);
    }
  }

  @Override
  protected void preUpdateAlgebraicNotation(Board board) {
    super.preUpdateAlgebraicNotation(board);
    setAlgebraicNotation(getAlgebraicNotation() + promoteInto);
  }

  public Character getPromoteInto() {
    return promoteInto;
  }

  public void setPromoteInto(char promoteInto) {
    if (promoteInto == 'B' || promoteInto == 'N' || promoteInto == 'R' || promoteInto == 'Q') {
      this.promoteInto = promoteInto;
    }
  }
}