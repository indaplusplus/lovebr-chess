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
    Piece knight = board.add(new Knight(WHITE), new Pos("d4"));
    assertEquals(
        new HashSet<>(
            (Arrays.asList(
                new Pos("c2"),
                new Pos("e2"),
                new Pos("b3"),
                new Pos("f3"),
                new Pos("b5"),
                new Pos("f5"),
                new Pos("c6"),
                new Pos("e6")))),
        knight.legalMoves().keySet());
  }
}
