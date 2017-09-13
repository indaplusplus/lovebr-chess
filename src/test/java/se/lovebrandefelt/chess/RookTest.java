package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
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
    Rook rook = board.add(new Rook(WHITE), new Pos("D4"));
    board.add(new Pawn(WHITE), new Pos("D2"));
    board.add(new Pawn(BLACK), new Pos("D6"));
    assertEquals(
        new HashSet<>(
            Arrays.asList(
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
        rook.legalMoves().stream().map(Move::getTo).collect(Collectors.toSet()));
  }
}
