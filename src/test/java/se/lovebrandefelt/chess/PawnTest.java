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
    Pawn whitePawn = board.add(new Pawn(WHITE), new Position(0, 0));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Position(7, 7));
    assertTrue(whitePawn.validMoves().contains(new Position(1, 0)));
    assertTrue(blackPawn.validMoves().contains(new Position(6, 7)));
  }

  @Test
  void cantCaptureOneStepForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Position(3, 4));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Position(4, 4));
    assertFalse(whitePawn.validMoves().contains(new Position(4, 4)));
    assertFalse(blackPawn.validMoves().contains(new Position(3, 4)));
  }

  @Test
  void canCaptureOneStepDiagonallyForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Position(3, 4));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Position(4, 3));
    assertTrue(whitePawn.validMoves().contains(new Position(4, 3)));
    assertTrue(blackPawn.validMoves().contains(new Position(3, 4)));
  }

  @Test
  void cantMoveOneStepDiagonallyForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Position(0, 0));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Position(7, 7));
    assertFalse(whitePawn.validMoves().contains(new Position(1, 1)));
    assertFalse(blackPawn.validMoves().contains(new Position(6, 6)));
  }
}
