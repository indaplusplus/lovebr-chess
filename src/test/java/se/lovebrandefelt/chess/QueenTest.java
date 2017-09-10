package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueenTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void canMoveDiogonallyOrOrthogonallyButNotThroughPieces() {
    Queen queen = board.add(new Queen(WHITE), new Pos(3, 3));
    board.add(new Pawn(WHITE), new Pos(1, 1));
    board.add(new Pawn(BLACK), new Pos(1, 5));
    board.add(new Pawn(WHITE), new Pos(1, 3));
    board.add(new Pawn(BLACK), new Pos(5, 3));
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
                new Pos("A7"),
                new Pos("D3"),
                new Pos("D5"),
                new Pos("D6"),
                new Pos("C4"),
                new Pos("B4"),
                new Pos("A4"),
                new Pos("E4"),
                new Pos("F4"),
                new Pos("G4"),
                new Pos("H4"))),
        queen.legalMoves());
  }
}
