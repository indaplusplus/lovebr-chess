package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.standardSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
  private Game game;

  @BeforeEach
  void beforeEach() {
    game = new Game(standardSetup(), WHITE);
  }

  @Test
  void makingAValidMoveShouldUpdateTheBoard() {
    Pos from = new Pos("B1");
    Pos to = new Pos("C3");
    Piece toBeMoved = game.getBoard().get(from);
    game.makeMove(from, to);
    assertSame(null, game.getBoard().get(from));
    assertSame(toBeMoved, game.getBoard().get(to));
  }

  @Test
  void makingAValidMoveShouldUpdateTheMovedPiece() {
    Pos from = new Pos("B1");
    Pos to = new Pos("C3");
    Piece toBeMoved = game.getBoard().get(from);
    game.makeMove(from, to);
    assertEquals(toBeMoved.getPos().getRow(), to.getRow());
    assertEquals(toBeMoved.getPos().getCol(), to.getCol());
  }

  @Test
  void onlyTheCurrentPlayerCanMoveHisPieces() {
    Pos from = new Pos("B8");
    Pos to = new Pos("C6");
    game.makeMove(from,  to);
    assertFalse(game.legalMoves().get(from).containsKey(to));
  }

  @Test
  void makingAValidMoveShouldPassTheTurn() {
    Pos from = new Pos("B1");
    Pos to = new Pos("C3");
    game.makeMove(from, to);
    assertEquals(BLACK, game.getCurrentPlayer());
  }
}
