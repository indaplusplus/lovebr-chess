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
    Piece whitePawn = board.add(new Pawn(WHITE), new Pos("a1"));
    Piece blackPawn = board.add(new Pawn(BLACK), new Pos("h8"));
    assertTrue(whitePawn.legalMoves().containsKey(new Pos("a2")));
    assertTrue(blackPawn.legalMoves().containsKey(new Pos("h7")));
  }

  @Test
  void cantCaptureOneStepForward() {
    Piece whitePawn = board.add(new Pawn(WHITE), new Pos("e4"));
    Piece blackPawn = board.add(new Pawn(BLACK), new Pos("e5"));
    assertFalse(whitePawn.legalMoves().containsKey(new Pos("e5")));
    assertFalse(blackPawn.legalMoves().containsKey(new Pos("e4")));
  }

  @Test
  void canCaptureOneStepDiagonallyForward() {
    Piece whitePawn = board.add(new Pawn(WHITE), new Pos("e4"));
    Piece blackPawn = board.add(new Pawn(BLACK), new Pos("d5"));
    assertTrue(whitePawn.legalMoves().containsKey(new Pos("d5")));
    assertTrue(blackPawn.legalMoves().containsKey(new Pos("e4")));
  }

  @Test
  void cantMoveOneStepDiagonallyForward() {
    Piece whitePawn = board.add(new Pawn(WHITE), new Pos("a1"));
    Piece blackPawn = board.add(new Pawn(BLACK), new Pos("h8"));
    assertFalse(whitePawn.legalMoves().containsKey(new Pos("b2")));
    assertFalse(blackPawn.legalMoves().containsKey(new Pos("g7")));
  }

  @Test
  void canMoveTwoStepsForwardOnFirstMove() {
    Piece whitePawn = board.add(new Pawn(WHITE), new Pos("a1"));
    Piece blackPawn = board.add(new Pawn(BLACK), new Pos("h8"));
    assertTrue(whitePawn.legalMoves().containsKey(new Pos("a3")));
    assertTrue(blackPawn.legalMoves().containsKey(new Pos("h6")));
  }

  @Test
  void cantMoveTwoStepsOnNonFirstMove() {
    Piece whitePawn = board.add(new Pawn(WHITE), new Pos("a1"));
    board.move(new Move(whitePawn.getPos(), new Pos("a2")));
    Piece blackPawn = board.add(new Pawn(BLACK), new Pos("h8"));
    board.move(new Move(blackPawn.getPos(), new Pos("h7")));
    assertFalse(whitePawn.legalMoves().containsKey(new Pos("a4")));
    assertFalse(blackPawn.legalMoves().containsKey(new Pos("h5")));
  }

  @Test
  void canEnPassant() {
    Piece whitePawn = board.add(new Pawn(WHITE), new Pos("a2"));
    Piece blackPawn = board.add(new Pawn(BLACK), new Pos("b4"));
    game.makeMove(whitePawn.getPos(), new Pos("a4"));
    assertTrue(blackPawn.legalMoves().containsKey(new Pos("a3")));
    game.makeMove(blackPawn.getPos(), new Pos("a3"));
    Move lastMove = board.getHistory().peek();
    assertSame(whitePawn, lastMove.getCaptured());
    assertSame(blackPawn, board.get(new Pos("a3")));
    assertNull(board.get(new Pos("a4")));
  }

  @Test
  void canPromote() {
    Pawn pawn = (Pawn) board.add(new Pawn(WHITE), new Pos("e7"));
    game.makeMove(new Pos("e7"), new Pos("e8"));
    assertTrue(pawn.canPromote());
  }

  @Test
  void promotionCanBeDoneAndUndone() {
    Pawn pawn = (Pawn) board.add(new Pawn(WHITE), new Pos("e7"));
    game.makeMove(new Pos("e7"), new Pos("e8"));
    pawn.promoteInto('N');
    board.undoMove();
    assertNull(board.get(new Pos("e8")));
    assertSame(pawn, board.get(new Pos("e7")));
  }
}
