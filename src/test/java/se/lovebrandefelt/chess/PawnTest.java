package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lovebrandefelt.chess.Board.MoveEvent;

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
    assertTrue(whitePawn.legalMoves().contains(new Pos("A2")));
    assertTrue(blackPawn.legalMoves().contains(new Pos("H7")));
  }

  @Test
  void cantCaptureOneStepForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("E4"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("E5"));
    assertFalse(whitePawn.legalMoves().contains(new Pos("E5")));
    assertFalse(blackPawn.legalMoves().contains(new Pos("E4")));
  }

  @Test
  void canCaptureOneStepDiagonallyForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("E4"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("D5"));
    assertTrue(whitePawn.legalMoves().contains(new Pos("D5")));
    assertTrue(blackPawn.legalMoves().contains(new Pos("E4")));
  }

  @Test
  void cantMoveOneStepDiagonallyForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A1"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("H8"));
    assertFalse(whitePawn.legalMoves().contains(new Pos("B2")));
    assertFalse(blackPawn.legalMoves().contains(new Pos("G7")));
  }

  @Test
  void canMoveTwoStepsForwardOnFirstMove() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A1"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("H8"));
    assertTrue(whitePawn.legalMoves().contains(new Pos("A3")));
    assertTrue(blackPawn.legalMoves().contains(new Pos("H6")));
  }

  @Test
  void cantMoveTwoStepsOnNonFirstMove() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A1"));
    board.move(whitePawn.getPos(), new Pos("A2"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("H8"));
    board.move(blackPawn.getPos(), new Pos("H7"));
    assertFalse(whitePawn.legalMoves().contains(new Pos("A4")));
    assertFalse(blackPawn.legalMoves().contains(new Pos("H5")));
  }

  @Test
  void canEnPassant() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A2"));
    board.move(whitePawn.getPos(), new Pos("A4"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("B4"));
    board.move(blackPawn.getPos(), new Pos("A3"));
    MoveEvent lastMove = board.getHistory().peek();
    assertSame(whitePawn, lastMove.getCaptured());
    assertEquals(new Pos("A4"), lastMove.getTo());
    assertSame(blackPawn, board.get(new Pos("A3")));
    assertNull(board.get(new Pos("A4")));
  }
}
