package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KingTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveToAdjacentSquares() {
    King king = board.add(new King(WHITE), new Pos(3, 3));
    assertEquals(
        new HashSet<>(
            (Arrays.asList(
                new Pos(2, 2),
                new Pos(2, 3),
                new Pos(2, 4),
                new Pos(3, 2),
                new Pos(3, 4),
                new Pos(4, 2),
                new Pos(4, 3),
                new Pos(4, 4)))),
        king.validMoves());
  }
}
