package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KingTest {
  private Board board;
  private Game game;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
    game = new Game(board, WHITE);
  }

  @Test
  void canMoveToAdjacentSquares() {
    King king = board.add(new King(WHITE), new Pos("D4"));
    assertTrue(
        king.legalMoves()
            .keySet()
            .containsAll(
                new HashSet<>(
                    (Arrays.asList(
                        new Pos("C3"),
                        new Pos("D3"),
                        new Pos("E3"),
                        new Pos("C4"),
                        new Pos("E4"),
                        new Pos("C5"),
                        new Pos("D5"),
                        new Pos("E5"))))));
  }

  @Test
  void cantMoveIntoCheck() {
    King king = board.add(new King(WHITE), new Pos("D4"));
    board.add(new Pawn(BLACK), new Pos("D6"));
    assertFalse(game.legalMoves().get(king.getPos()).containsKey(new Pos("C5")));
    assertFalse(game.legalMoves().get(king.getPos()).containsKey(new Pos("E5")));
  }

  @Test
  void cantBeCheckedByOwnPieces() {
    King king = board.add(new King(WHITE), new Pos("D4"));
    board.add(new Pawn(WHITE), new Pos("D6"));
    assertTrue(game.legalMoves().get(king.getPos()).containsKey(new Pos("C5")));
    assertTrue(game.legalMoves().get(king.getPos()).containsKey(new Pos("E5")));
  }

  @Test
  void canCastleLeft() {
    King king = board.add(new King(WHITE), new Pos("E1"));
    final Rook rook = board.add(new Rook(WHITE), new Pos("A1"));
    assertTrue(game.legalMoves().get(king.getPos()).containsKey(new Pos("C1")));
    game.makeMove(new Pos("E1"), new Pos("C1"));
    assertSame(king, board.get(new Pos("C1")));
    assertSame(rook, board.get(new Pos("D1")));
  }

  @Test
  void canCastleRight() {
    King king = board.add(new King(WHITE), new Pos("E1"));
    final Rook rook = board.add(new Rook(WHITE), new Pos("H1"));
    assertTrue(game.legalMoves().get(king.getPos()).containsKey(new Pos("G1")));
    game.makeMove(new Pos("E1"), new Pos("G1"));
    assertSame(king, board.get(new Pos("G1")));
    assertSame(rook, board.get(new Pos("F1")));
  }

  @Test
  void cantCastleWhenThreatenedSquaresInBetween() {
    King king = board.add(new King(WHITE), new Pos("E1"));
    board.add(new Rook(WHITE), new Pos("H1"));
    board.add(new Rook(BLACK), new Pos("F2"));
    assertFalse(game.legalMoves().get(king.getPos()).containsKey(new Pos("G1")));
  }
}
