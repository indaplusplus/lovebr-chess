package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BishopTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveDiogonallyButNotThroughPieces() {
    Bishop bishop = board.add(new Bishop(WHITE), new Pos("D4"));
    board.add(new Pawn(WHITE), new Pos("B2"));
    board.add(new Pawn(BLACK), new Pos("F2"));
    assertEquals(
        new HashSet<>(
            Arrays.asList(
                new Pos("C3"),
                new Pos("E3"),
                new Pos("F2"),
                new Pos("E5"),
                new Pos("F6"),
                new Pos("G7"),
                new Pos("H8"),
                new Pos("C5"),
                new Pos("B6"),
                new Pos("A7"))),
        bishop.legalMoves().stream().map(Move::getTo).collect(Collectors.toSet()));
  }
}
