package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PawnTest {
  private Game game;
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
    game = new Game(board, WHITE);
  }

  @Test
  void canMoveOneStepForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A1"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("H8"));
    assertTrue(
        whitePawn.legalMoves().containsKey(new Pos("A2")));
    assertTrue(
        blackPawn.legalMoves().containsKey(new Pos("H7")));
  }

  @Test
  void cantCaptureOneStepForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("E4"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("E5"));
    assertFalse(
        whitePawn.legalMoves().containsKey(new Pos("E5")));
    assertFalse(
        blackPawn.legalMoves().containsKey(new Pos("E4")));
  }

  @Test
  void canCaptureOneStepDiagonallyForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("E4"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("D5"));
    assertTrue(
        whitePawn.legalMoves().containsKey(new Pos("D5")));
    assertTrue(
        blackPawn.legalMoves().containsKey(new Pos("E4")));
  }

  @Test
  void cantMoveOneStepDiagonallyForward() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A1"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("H8"));
    assertFalse(
        whitePawn.legalMoves().containsKey(new Pos("B2")));
    assertFalse(
        blackPawn.legalMoves().containsKey(new Pos("G7")));
  }

  @Test
  void canMoveTwoStepsForwardOnFirstMove() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A1"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("H8"));
    assertTrue(
        whitePawn.legalMoves().containsKey(new Pos("A3")));
    assertTrue(
        blackPawn.legalMoves().containsKey(new Pos("H6")));
  }

  @Test
  void cantMoveTwoStepsOnNonFirstMove() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A1"));
    board.move(new Move(whitePawn.getPos(), new Pos("A2")));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("H8"));
    board.move(new Move(blackPawn.getPos(), new Pos("H7")));
    assertFalse(
        whitePawn.legalMoves().containsKey(new Pos("A4")));
    assertFalse(
        blackPawn.legalMoves().containsKey(new Pos("H5")));
  }

  @Test
  void canEnPassant() {
    Pawn whitePawn = board.add(new Pawn(WHITE), new Pos("A2"));
    Pawn blackPawn = board.add(new Pawn(BLACK), new Pos("B4"));
    game.makeMove(whitePawn.getPos(), new Pos("A4"));
    assertTrue(
        blackPawn.legalMoves().containsKey(new Pos("A3")));
    game.makeMove(blackPawn.getPos(), new Pos("A3"));
    Move lastMove = board.getHistory().peek();
    assertSame(whitePawn, lastMove.getCaptured());
    assertSame(blackPawn, board.get(new Pos("A3")));
    assertNull(board.get(new Pos("A4")));
  }

  @Test
  void canPromote() {
    Pawn pawn = board.add(new Pawn(WHITE), new Pos("E7"));
    game.makeMove(new Pos("E7"), new Pos("E8"));
    assertTrue(pawn.canPromote());
  }

  @Test void
  promotionCanBeDoneAndUndone() {
    Pawn pawn = board.add(new Pawn(WHITE), new Pos("E7"));
    game.makeMove(new Pos("E7"), new Pos("E8"));
    pawn.promote(new Knight(WHITE));
    board.undoMove();
    assertNull(board.get(new Pos("E8")));
    assertSame(pawn, board.get(new Pos("E7")));
  }
}
