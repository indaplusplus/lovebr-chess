package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.lovebrandefelt.chess.Pos.colToString;
import static se.lovebrandefelt.chess.Pos.rowToString;

import org.junit.jupiter.api.Test;

class PosStringTest {
  @Test
  void posStringConstructorReturnsTheCorrectPos() {
    assertEquals(new Pos(3, 5), new Pos("F4"));
    assertEquals(new Pos(11, 0), new Pos("A12"));
    assertEquals(new Pos(9, 26), new Pos("AA10"));
  }

  @Test
  void posStringConstructorThrowsIllegalArgumentExceptionOnIncorrectStrings() {
    assertThrows(IllegalArgumentException.class, () -> new Pos("C"));
    assertThrows(IllegalArgumentException.class, () -> new Pos("6"));
  }

  @Test
  void posRowToStringReturnsCorrectString() {
    assertEquals("5", rowToString(4));
    assertEquals("12", rowToString(11));
  }

  @Test
  void posColumnToStringReturnsCorrectString() {
    assertEquals("U", colToString(20));
    assertEquals("BC", colToString(54));
  }
}
