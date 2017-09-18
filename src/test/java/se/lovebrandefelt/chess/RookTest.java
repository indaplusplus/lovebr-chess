package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RookTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveOrthogonallyButNotThroughPieces() {
    Piece rook = board.add(new Rook(WHITE), new Pos("d4"));
    board.add(new Pawn(WHITE), new Pos("d2"));
    board.add(new Pawn(BLACK), new Pos("d6"));
    assertEquals(
        new HashSet<>(
            Arrays.asList(
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
        rook.legalMoves().keySet());
  }
}
