package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueenTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveDiagonallyOrOrthogonallyButNotThroughPieces() {
    final Piece queen = board.add(new Queen(WHITE), new Pos(3, 3));
    board.add(new Pawn(WHITE), new Pos(1, 1));
    board.add(new Pawn(BLACK), new Pos(1, 5));
    board.add(new Pawn(WHITE), new Pos(1, 3));
    board.add(new Pawn(BLACK), new Pos(5, 3));
    assertEquals(
        new HashSet<>(
            Arrays.asList(
                new Pos("c3"),
                new Pos("e3"),
                new Pos("f2"),
                new Pos("e5"),
                new Pos("f6"),
                new Pos("g7"),
                new Pos("h8"),
                new Pos("c5"),
                new Pos("b6"),
                new Pos("a7"),
                new Pos("d3"),
                new Pos("d5"),
                new Pos("d6"),
                new Pos("c4"),
                new Pos("b4"),
                new Pos("a4"),
                new Pos("e4"),
                new Pos("f4"),
                new Pos("g4"),
                new Pos("h4"))),
        queen.legalMoves().keySet());
  }
}
