package se.lovebrandefelt.chess;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KingTest {
  private Board board;
  private Game game;

  @BeforeEach
  void beforeEach() {
    board = new Board(8, 8);
    game = new Game(board, WHITE);
  }

  @Test
  void canMoveToAdjacentSquares() {
    Piece king = board.add(new King(WHITE), new Pos("d4"));
    assertTrue(
        king.legalMoves()
            .keySet()
            .containsAll(
                new HashSet<>(
                    (Arrays.asList(
                        new Pos("c3"),
                        new Pos("d3"),
                        new Pos("e3"),
                        new Pos("c4"),
                        new Pos("e4"),
                        new Pos("c5"),
                        new Pos("d5"),
                        new Pos("e5"))))));
  }

  @Test
  void cantMoveIntoCheck() {
    Piece king = board.add(new King(WHITE), new Pos("d4"));
    board.add(new Pawn(BLACK), new Pos("d6"));
    assertFalse(game.legalMoves().get(king.getPos()).containsKey(new Pos("c5")));
    assertFalse(game.legalMoves().get(king.getPos()).containsKey(new Pos("e5")));
  }

  @Test
  void cantBeCheckedByOwnPieces() {
    Piece king = board.add(new King(WHITE), new Pos("d4"));
    board.add(new Pawn(WHITE), new Pos("d6"));
    assertTrue(game.legalMoves().get(king.getPos()).containsKey(new Pos("c5")));
    assertTrue(game.legalMoves().get(king.getPos()).containsKey(new Pos("e5")));
  }

  @Test
  void canCastleLeft() {
    Piece king = board.add(new King(WHITE), new Pos("e1"));
    final Piece rook = board.add(new Rook(WHITE), new Pos("a1"));
    assertTrue(game.legalMoves().get(king.getPos()).containsKey(new Pos("c1")));
    game.makeMove(new Pos("e1"), new Pos("c1"));
    assertSame(king, board.get(new Pos("c1")));
    assertSame(rook, board.get(new Pos("d1")));
  }

  @Test
  void canCastleRight() {
    Piece king = board.add(new King(WHITE), new Pos("e1"));
    final Piece rook = board.add(new Rook(WHITE), new Pos("h1"));
    assertTrue(game.legalMoves().get(king.getPos()).containsKey(new Pos("g1")));
    game.makeMove(new Pos("e1"), new Pos("g1"));
    assertSame(king, board.get(new Pos("g1")));
    assertSame(rook, board.get(new Pos("f1")));
  }

  @Test
  void cantCastleLeftWhenThreatenedSquaresInBetween() {
    Piece king = board.add(new King(WHITE), new Pos("e1"));
    board.add(new Rook(WHITE), new Pos("a1"));
    board.add(new Rook(BLACK), new Pos("d2"));
    assertFalse(game.legalMoves().get(king.getPos()).containsKey(new Pos("c1")));
  }

  @Test
  void cantCastleRightWhenThreatenedSquaresInBetween() {
    Piece king = board.add(new King(WHITE), new Pos("e1"));
    board.add(new Rook(WHITE), new Pos("h1"));
    board.add(new Rook(BLACK), new Pos("f2"));
    assertFalse(game.legalMoves().get(king.getPos()).containsKey(new Pos("g1")));
  }

  @Test
  void canCastleFromSecondColumnInFischerChess() {
    Piece king = board.add(new King(WHITE), new Pos("b1"));
    board.add(new Rook(WHITE), new Pos("a1"));
    assertTrue(game.legalMoves().get(king.getPos()).containsKey(new Pos("c1")));
  }
}
