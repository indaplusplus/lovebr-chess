package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KingTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveToAdjacentSquares() {
    King king = board.add(new King(WHITE), new Position(3, 3));
    assertTrue(
        king.validMoves()
            .containsAll(
                Arrays.asList(
                    new Position(2, 2),
                    new Position(2, 3),
                    new Position(2, 4),
                    new Position(3, 2),
                    new Position(3, 4),
                    new Position(4, 2),
                    new Position(4, 3),
                    new Position(4, 4))));
  }
}
