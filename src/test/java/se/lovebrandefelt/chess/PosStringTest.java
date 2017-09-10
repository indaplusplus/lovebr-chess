package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PosStringTest {
  @Test
  void posStringConstructorReturnsTheCorrectPos() {
    Pos f4 = new Pos("F4");
    Pos a12 = new Pos("A12");
    Pos aa10 = new Pos("AA10");
    assertEquals(new Pos(3, 5), f4);
    assertEquals(new Pos(11, 0), a12);
    assertEquals(new Pos(9, 26), aa10);
  }

  @Test
  void posColumnToStringReturnsCorrectString() {
    Pos col20 = new Pos(0, 20);
    Pos col54 = new Pos(0, 54);
    assertEquals("U", col20.colToString());
    assertEquals("BC", col54.colToString());
  }
}
