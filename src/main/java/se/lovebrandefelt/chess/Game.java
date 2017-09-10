package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Game {
  public static final Board DEFAULT_SETUP =
      new SetupBuilder(8, 8)
          .add(new Rook(WHITE), new Pos(0, 0))
          .add(new Knight(WHITE), new Pos(0, 1))
          .add(new Bishop(WHITE), new Pos(0, 2))
          .add(new Queen(WHITE), new Pos(0, 3))
          .add(new King(WHITE), new Pos(0, 4))
          .add(new Bishop(WHITE), new Pos(0, 5))
          .add(new Knight(WHITE), new Pos(0, 6))
          .add(new Rook(WHITE), new Pos(0, 7))
          .addPawnRow(WHITE, 1)
          .add(new Rook(BLACK), new Pos(7, 0))
          .add(new Knight(BLACK), new Pos(7, 1))
          .add(new Bishop(BLACK), new Pos(7, 2))
          .add(new Queen(BLACK), new Pos(7, 3))
          .add(new King(BLACK), new Pos(7, 4))
          .add(new Bishop(BLACK), new Pos(7, 5))
          .add(new Knight(BLACK), new Pos(7, 6))
          .add(new Rook(BLACK), new Pos(7, 7))
          .addPawnRow(BLACK, 6)
          .create();

  private Board board;
  private Color currentPlayer;
  private Map<Color, List<Piece>> captures;

  public <T extends Piece> Game(Board setup, Color startingPlayer) {
    board = setup;
    currentPlayer = startingPlayer;
    captures = new HashMap<>();
    captures.put(WHITE, new ArrayList<>());
    captures.put(BLACK, new ArrayList<>());
  }

  public boolean makeMove(Pos from, Pos to) {
    if (!board.isEmpty(from) && board.get(from).getColor() == currentPlayer) {
      Set<Pos> validMoves = board.get(from).validMoves();
      if (validMoves.contains(to)) {
        if (!board.isEmpty(to)) {
          captures.get(currentPlayer).add(board.get(to));
        }
        board.move(from, to);
        currentPlayer = Color.values()[(currentPlayer.ordinal() + 1) % Color.values().length];
        return true;
      }
    }
    return false;
  }

  public Board getBoard() {
    return board;
  }

  public Color getCurrentPlayer() {
    return currentPlayer;
  }

  public Map<Color, List<Piece>> getCaptures() {
    return captures;
  }
}
