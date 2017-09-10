package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PawnTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveOneStepForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A1"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("H8"));
    assertTrue(whitePawn.validMoves().contains(new Pos("A2")));
    assertTrue(blackPawn.validMoves().contains(new Pos("H7")));
  }

  @Test
  void cantCaptureOneStepForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("E4"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("E5"));
    assertFalse(whitePawn.validMoves().contains(new Pos("E5")));
    assertFalse(blackPawn.validMoves().contains(new Pos("E4")));
  }

  @Test
  void canCaptureOneStepDiagonallyForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos(3, 4));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos(4, 3));
    assertTrue(whitePawn.validMoves().contains(new Pos(4, 3)));
    assertTrue(blackPawn.validMoves().contains(new Pos(3, 4)));
  }

  @Test
  void cantMoveOneStepDiagonallyForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos(0, 0));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos(7, 7));
    assertFalse(whitePawn.validMoves().contains(new Pos(1, 1)));
    assertFalse(blackPawn.validMoves().contains(new Pos(6, 6)));
  }
}
