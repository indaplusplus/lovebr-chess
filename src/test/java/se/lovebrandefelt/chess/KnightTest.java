package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
  void canMoveOneStepInOneDirectionAndTwoStepsInTheOtherDirection() {
    Knight knight = board.add(new Knight(WHITE), new Position(3, 3));
    knight
        .validMoves()
        .forEach((position -> System.out.println(position.getRow() + " " + position.getColumn())));
    assertTrue(
        knight
            .validMoves()
            .equals(
                new HashSet<>(
                    Arrays.asList(
                        new Position(1, 2),
                        new Position(1, 4),
                        new Position(2, 1),
                        new Position(2, 5),
                        new Position(4, 1),
                        new Position(4, 5),
                        new Position(5, 2),
                        new Position(5, 4)))));
  }
}
