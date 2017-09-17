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
    Piece knight = board.add(new Knight(WHITE), new Pos("D4"));
    assertEquals(
        new HashSet<>(
            (Arrays.asList(
                new Pos("C2"),
                new Pos("E2"),
                new Pos("B3"),
                new Pos("F3"),
                new Pos("B5"),
                new Pos("F5"),
                new Pos("C6"),
                new Pos("E6")))),
        knight.legalMoves().keySet());
  }
}
