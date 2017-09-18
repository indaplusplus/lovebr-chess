package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BishopTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveDiagonallyButNotThroughPieces() {
    Piece bishop = board.add(new Bishop(WHITE), new Pos("d4"));
    board.add(new Pawn(WHITE), new Pos("b2"));
    board.add(new Pawn(BLACK), new Pos("f2"));
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
                new Pos("a7"))),
        bishop.legalMoves().keySet());
  }
}
