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
        king.validMoves());
  }
}
