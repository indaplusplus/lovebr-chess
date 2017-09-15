package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.State.WHITE_WON;
import static se.lovebrandefelt.chess.Game.standardSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStateTest {
  private Game game;
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
    game = new Game(board, WHITE);
  }

  @Test
  void whiteCanWin() {
    board.add(new Rook(WHITE), new Pos("H6"));
    board.add(new Rook(WHITE), new Pos("G7"));
    board.add(new King(BLACK), new Pos("B8"));
    game.makeMove(new Pos("H6"), new Pos("H8"));
    assertEquals(WHITE_WON, game.result());
  }
}
