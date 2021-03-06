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
