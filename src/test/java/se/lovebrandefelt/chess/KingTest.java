package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
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
    assertEquals(
        new HashSet<>(
            (Arrays.asList(
                new Pos("C3"),
                new Pos("D3"),
                new Pos("E3"),
                new Pos("C4"),
                new Pos("E4"),
                new Pos("C5"),
                new Pos("D5"),
                new Pos("E5")))),
        king.legalMoves().stream().map(Move::getTo).collect(Collectors.toSet()));
  }

  @Test
  void cantMoveIntoCheck() {
    King king = board.add(new King(WHITE), new Pos("D4"));
    board.add(new Pawn(BLACK), new Pos("D6"));
    assertFalse(
        game.legalMovesWithCheck(king.getPos())
            .stream()
            .anyMatch((move) -> move.getTo().equals(new Pos("C5"))));
    assertFalse(
        game.legalMovesWithCheck(king.getPos())
            .stream()
            .anyMatch((move) -> move.getTo().equals(new Pos("E5"))));
  }

  @Test
  void cantBeCheckedByOwnPieces() {
    King king = board.add(new King(WHITE), new Pos("D4"));
    board.add(new Pawn(WHITE), new Pos("D6"));
    assertTrue(
        game.legalMovesWithCheck(king.getPos())
            .stream()
            .anyMatch((move) -> move.getTo().equals(new Pos("C5"))));
    assertTrue(
        game.legalMovesWithCheck(king.getPos())
            .stream()
            .anyMatch((move) -> move.getTo().equals(new Pos("E5"))));
  }
}
