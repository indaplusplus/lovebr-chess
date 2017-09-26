package se.lovebrandefelt.chess;

import java.util.Map;

public class SilvermanKing extends King {

  public SilvermanKing(Color color) {
    super(color);
  }

  @Override
  public Map<Pos, Move> legalMoves() {
    return super.recursionSafeLegalMoves();
  }
}
