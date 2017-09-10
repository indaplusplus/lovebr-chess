package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnightTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveOneStepInOneDirectionAndTwoStepsInOnePerpendicularDirection() {
    Knight knight = board.add(new Knight(WHITE), new Pos(3, 3));
    assertEquals(
        new HashSet<>(
            (Arrays.asList(
                new Pos(1, 2),
                new Pos(1, 4),
                new Pos(2, 1),
                new Pos(2, 5),
                new Pos(4, 1),
                new Pos(4, 5),
                new Pos(5, 2),
                new Pos(5, 4)))),
        knight.validMoves());
  }
}
