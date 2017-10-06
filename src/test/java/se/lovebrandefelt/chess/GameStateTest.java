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
    board.add(new Rook(WHITE), new Pos("h6"));
    board.add(new Rook(WHITE), new Pos("g7"));
    board.add(new King(BLACK), new Pos("b8"));
    game.updateState();
    assertEquals(IN_PROGRESS, game.getState());
  }

  @Test
  void whiteCanWin() {
    board.add(new Rook(WHITE), new Pos("h6"));
    board.add(new Rook(WHITE), new Pos("g7"));
    board.add(new King(BLACK), new Pos("b8"));
    game.makeMove(new Pos("h6"), new Pos("h8"));
    assertEquals(WHITE_WON, game.getState());
  }

  @Test
  void blackCanWin() {
    board.add(new Rook(BLACK), new Pos("h8"));
    board.add(new Rook(BLACK), new Pos("g7"));
    board.add(new King(WHITE), new Pos("b8"));
    game.updateState();
    assertEquals(BLACK_WON, game.getState());
  }

  @Test
  void gameEndsInDrawWhenOnePlayerCantMove() {
    board.add(new Rook(BLACK), new Pos("h7"));
    board.add(new Rook(BLACK), new Pos("b1"));
    board.add(new King(WHITE), new Pos("a8"));
    game.updateState();
    assertEquals(DRAW, game.getState());
  }
}
