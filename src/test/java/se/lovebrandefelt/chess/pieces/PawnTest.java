package se.lovebrandefelt.chess.pieces;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import se.lovebrandefelt.chess.Board;
import se.lovebrandefelt.chess.Position;

class PawnTest {
  @Test
  void pawnsCanMoveOneStepForward() {
    Board board = new Board(8, 8);
    Pawn pawn = new Pawn(board, new Position(0, 0));
    assertTrue(pawn.validMoves().contains(new Position(1, 0)));
  }
}
