package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.chess960Setup;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Chess960Test {
  private Board board;

  @BeforeEach
  void beforeEach() {
    board = chess960Setup();
  }

  @Test
  void theKingShouldStartInBetweenTheRooks() {
    Piece king =
        board
            .getPieces()
            .get(WHITE)
            .stream()
            .filter((piece) -> piece.getTypeId() == 'K')
            .findFirst()
            .orElseThrow(NullPointerException::new);
    List<Piece> rooks =
        board
            .getPieces()
            .get(WHITE)
            .stream()
            .filter((piece) -> piece.getTypeId() == 'R')
            .collect(Collectors.toList());
    assertTrue(
        Math.abs(
                Integer.compare(rooks.get(0).getPos().getCol(), king.getPos().getCol())
                    - Integer.compare(rooks.get(1).getPos().getCol(), king.getPos().getCol()))
            == 2);
  }

  @Test
  void bishopsShouldStartOnOppositeSquareColors() {
    List<Piece> bishops =
        board
            .getPieces()
            .get(WHITE)
            .stream()
            .filter((piece) -> piece.getTypeId() == 'B')
            .collect(Collectors.toList());
    assertTrue(bishops.get(0).getPos().getCol() % 2 != bishops.get(1).getPos().getCol() % 2);
  }
}
