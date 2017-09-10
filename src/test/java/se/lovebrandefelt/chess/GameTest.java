package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertSame;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.DEFAULT_SETUP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
  private Game game;

  @BeforeEach
  void beforeEach() {
    game = new Game(DEFAULT_SETUP, WHITE);
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
}
