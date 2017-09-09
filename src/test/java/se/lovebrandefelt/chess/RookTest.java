package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RookTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveOrthogonallyButNotThroughPieces() {
    Rook rook = board.add(new Rook(WHITE), new Position(3, 3));
    board.add(new Pawn(WHITE), new Position(1, 3));
    board.add(new Pawn(BLACK), new Position(5, 3));
    assertTrue(
        rook.validMoves()
            .equals(
                new HashSet<>(
                    Arrays.asList(
                        new Position(2, 3),
                        new Position(4, 3),
                        new Position(5, 3),
                        new Position(3, 2),
                        new Position(3, 1),
                        new Position(3, 0),
                        new Position(3, 4),
                        new Position(3, 5),
                        new Position(3, 6),
                        new Position(3, 7)))));
  }
}
