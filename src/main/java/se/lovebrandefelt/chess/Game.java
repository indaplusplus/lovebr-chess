package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.State.BLACK_WON;
import static se.lovebrandefelt.chess.Game.State.DRAW;
import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Game.State.WHITE_WON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
  private Board board;
  private Color currentPlayer;
  private Map<Pos, Map<Pos, Move>> legalMoves;
  public <T extends Piece> Game(Board setup, Color startingPlayer) {
    board = setup;
    currentPlayer = startingPlayer;
    legalMoves = new HashMap<>();
  }

  public static Board standardSetup() {
    Board board = new Board(8, 8);
    board.add(new Rook(WHITE), new Pos(0, 0));
    board.add(new Knight(WHITE), new Pos(0, 1));
    board.add(new Bishop(WHITE), new Pos(0, 2));
    board.add(new Queen(WHITE), new Pos(0, 3));
    board.add(new King(WHITE), new Pos(0, 4));
    board.add(new Bishop(WHITE), new Pos(0, 5));
    board.add(new Knight(WHITE), new Pos(0, 6));
    board.add(new Rook(WHITE), new Pos(0, 7));
    board.addPawnRow(WHITE, 1);
    board.add(new Rook(BLACK), new Pos(7, 0));
    board.add(new Knight(BLACK), new Pos(7, 1));
    board.add(new Bishop(BLACK), new Pos(7, 2));
    board.add(new Queen(BLACK), new Pos(7, 3));
    board.add(new King(BLACK), new Pos(7, 4));
    board.add(new Bishop(BLACK), new Pos(7, 5));
    board.add(new Knight(BLACK), new Pos(7, 6));
    board.add(new Rook(BLACK), new Pos(7, 7));
    board.addPawnRow(BLACK, 6);
    return board;
  }

  public static Board chess960Setup() {
    Board board = new Board(8, 8);
    Random random = new Random();
    List<Character> pieces = new ArrayList<>(Arrays.asList('R', 'R', 'N', 'N', 'B', 'B', 'Q', 'K'));
    while (pieces.indexOf('B') % 2 == pieces.lastIndexOf('B') % 2
        || pieces.indexOf('R') > pieces.indexOf('K')
        || pieces.lastIndexOf('R') < pieces.indexOf('K')) {
      for (int i = 0; i < 7; i++) {
        pieces.add(i, pieces.remove(random.nextInt(8 - i) + i));
      }
    }
    for (int i = 0; i < 8; i++) {
      switch (pieces.get(i)) {
        case 'B':
          board.add(new Bishop(WHITE), new Pos(0, i));
          board.add(new Bishop(BLACK), new Pos(7, i));
          break;
        case 'K':
          board.add(new King(WHITE), new Pos(0, i));
          board.add(new King(BLACK), new Pos(7, i));
          break;
        case 'N':
          board.add(new Knight(WHITE), new Pos(0, i));
          board.add(new Knight(BLACK), new Pos(7, i));
          break;
        case 'R':
          board.add(new Rook(WHITE), new Pos(0, i));
          board.add(new Rook(BLACK), new Pos(7, i));
          break;
        case 'Q':
          board.add(new Queen(WHITE), new Pos(0, i));
          board.add(new Queen(BLACK), new Pos(7, i));
          break;
        default:
      }
    }
    board.addPawnRow(WHITE, 1);
    board.addPawnRow(BLACK, 6);
    return board;
  }

  public Map<Pos, Map<Pos, Move>> legalMoves() {
    if (legalMoves.isEmpty()) {
      new ArrayList<>(getBoard().getPieces().get(currentPlayer))
          .forEach((piece) -> legalMoves.put(piece.getPos(), legalMovesWithCheck(piece.getPos())));
    }
    return legalMoves;
  }

  public Map<Pos, Move> legalMovesWithCheck(Pos from) {
    return board
        .get(from)
        .legalMoves()
        .entrySet()
        .stream()
        .filter((entry) -> !movePutsCurrentPlayerInCheck(entry.getValue()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  public boolean movePutsCurrentPlayerInCheck(Move move) {
    board.move(move);
    if (board.kingInCheck(currentPlayer)) {
      board.undoMove();
      return true;
    }
    board.undoMove();
    return false;
  }

  public void makeMove(Pos from, Pos to) {
    Move move = legalMoves().get(from).get(to);
    if (move != null) {
      board.move(move);
      currentPlayer = currentPlayer.next();
    }
    legalMoves = new HashMap<>();
  }

  public State state() {
    if (legalMoves().values().stream().anyMatch((moves) -> !moves.isEmpty())) {
      return IN_PROGRESS;
    }
    if (board.kingInCheck(currentPlayer)) {
      if (currentPlayer == WHITE) {
        return BLACK_WON;
      } else {
        return WHITE_WON;
      }
    }
    return DRAW;
  }

  public Board getBoard() {
    return board;
  }

  public Color getCurrentPlayer() {
    return currentPlayer;
  }

  public enum State {
    IN_PROGRESS,
    WHITE_WON,
    BLACK_WON,
    DRAW
  }
}
