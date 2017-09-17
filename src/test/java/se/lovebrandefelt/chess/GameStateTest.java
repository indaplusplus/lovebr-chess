package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.State.BLACK_WON;
import static se.lovebrandefelt.chess.Game.State.DRAW;
import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Game.State.WHITE_WON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameStateTest {
  private Game game;
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
    game = new Game(board, WHITE);
  }

  @Test
  void stateIsInProgressWhenGameHasNotEnded() {
    board.add(new Rook(WHITE), new Pos("H6"));
    board.add(new Rook(WHITE), new Pos("G7"));
    board.add(new King(BLACK), new Pos("B8"));
    assertEquals(IN_PROGRESS, game.state());
  }

  @Test
  void whiteCanWin() {
    board.add(new Rook(WHITE), new Pos("H6"));
    board.add(new Rook(WHITE), new Pos("G7"));
    board.add(new King(BLACK), new Pos("B8"));
    game.makeMove(new Pos("H6"), new Pos("H8"));
    assertEquals(WHITE_WON, game.state());
  }

  @Test
  void blackCanWin() {
    board.add(new Rook(BLACK), new Pos("H8"));
    board.add(new Rook(BLACK), new Pos("G7"));
    board.add(new King(WHITE), new Pos("B8"));
    assertEquals(BLACK_WON, game.state());
  }

  @Test
  void gameEndsInDrawWhenOnePlayerCantMove() {
    board.add(new Rook(BLACK), new Pos("H7"));
    board.add(new Rook(BLACK), new Pos("B1"));
    board.add(new King(WHITE), new Pos("A8"));
    assertEquals(DRAW, game.state());
  }
}
