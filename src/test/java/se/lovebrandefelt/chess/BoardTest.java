package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
  }

  @Test
  void removeReturnsNullWhenRemovingFromEmptySquare() {
    assertNull(board.remove(new Pos(0, 0)));
  }
}
