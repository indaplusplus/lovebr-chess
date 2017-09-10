package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.lovebrandefelt.chess.Pos.colToString;

import org.junit.jupiter.api.Test;

public class PosStringTest {
  @Test
  void posStringConstructorReturnsTheCorrectPos() {
    assertEquals(new Pos(3, 5), new Pos("F4"));
    assertEquals(new Pos(11, 0), new Pos("A12"));
    assertEquals(new Pos(9, 26), new Pos("AA10"));
  }

  @Test
  void posColumnToStringReturnsCorrectString() {
    assertEquals("U", colToString(20));
    assertEquals("BC", colToString(54));
  }
}
