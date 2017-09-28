package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.standardSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
  private Game game;
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = standardSetup();
    game = new Game(board, WHITE);
  }

  @Test
  void makingAValidMoveShouldUpdateTheBoard() {
    Pos from = new Pos("b1");
    Pos to = new Pos("c3");
    Piece toBeMoved = game.getBoard().get(from);
    game.makeMove(from, to);
    assertSame(null, game.getBoard().get(from));
    assertSame(toBeMoved, game.getBoard().get(to));
  }

  @Test
  void checkmateShouldPutGameToWinner() {
    // A simple checkmate setup
    board = new Board(8, 8);
    board.add(new King(WHITE), new Pos(0, 0));
    board.add(new Rook(WHITE), new Pos(6, 1));
    board.add(new Rook(WHITE), new Pos(7, 0));
    board.add(new King(BLACK), new Pos(7, 6));
    game = new Game(board, BLACK);
    assertEquals(Game.State.WHITE_WON, game.state());

    // D. Byrne vs. Fischer checkmate
    board = new Board(8, 8);
    board.add(new Queen(WHITE), new Pos(0, 1));
    board.add(new Pawn(BLACK), new Pos(1, 5));
    board.add(new King(BLACK), new Pos(1, 6));
    board.add(new Pawn(BLACK), new Pos(2, 2));
    board.add(new Pawn(BLACK), new Pos(2, 6));
    board.add(new Pawn(BLACK), new Pos(3, 1));
    board.add(new Knight(WHITE), new Pos(3, 4));
    board.add(new Pawn(BLACK), new Pos(3, 7));
    board.add(new Bishop(BLACK), new Pos(4, 1));
    board.add(new Pawn(WHITE), new Pos(4, 7));
    board.add(new Bishop(BLACK), new Pos(5, 1));
    board.add(new Knight(BLACK), new Pos(5, 2));
    board.add(new Rook(BLACK), new Pos(6, 2));
    board.add(new Pawn(WHITE), new Pos(6, 6));
    board.add(new King(WHITE), new Pos(7, 2));

    game = new Game(board, WHITE);
    assertEquals(Game.State.BLACK_WON, game.state());
  }

  @Test
  void makingAValidMoveShouldUpdateTheMovedPiece() {
    Pos from = new Pos("b1");
    Pos to = new Pos("c3");
    Piece toBeMoved = game.getBoard().get(from);
    game.makeMove(from, to);
    assertEquals(toBeMoved.getPos().getRow(), to.getRow());
    assertEquals(toBeMoved.getPos().getCol(), to.getCol());
  }

  @Test
  void onlyTheCurrentPlayerCanMoveHisPieces() {
    Piece pawn = board.add(new Pawn(BLACK), new Pos("e5"));
    assertFalse(game.legalMoves().containsKey(pawn.getPos()));
  }

  @Test
  void makingAValidMoveShouldPassTheTurn() {
    Pos from = new Pos("b1");
    Pos to = new Pos("c3");
    game.makeMove(from, to);
    assertEquals(BLACK, game.getCurrentPlayer());
  }
}
