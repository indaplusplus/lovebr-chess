package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.State.BLACK_WON;
import static se.lovebrandefelt.chess.Game.State.DRAW;
import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Game.State.WHITE_WON;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {
  public enum State {
    IN_PROGRESS,
    WHITE_WON,
    BLACK_WON,
    DRAW
  }

  private Board board;
  private Color currentPlayer;

  public <T extends Piece> Game(Board setup, Color startingPlayer) {
    board = setup;
    currentPlayer = startingPlayer;
  }

  public static Board defaultSetup() {
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

  public Set<Pos> validFroms() {
    Set<Pos> validFroms = new HashSet<>();
    for (int row = 0; row < getBoard().rows(); row++) {
      for (int col = 0; col < getBoard().columns(); col++) {
        Pos from = new Pos(row, col);
        if (legalMovesWithCheck(from).size() > 0) {
          validFroms.add(from);
        }
      }
    }
    return validFroms;
  }

  public void makeMove(Pos from, Pos to) {
    board.move(from, to);
    currentPlayer = currentPlayer.next();
  }

  public Set<Pos> legalMovesWithCheck(Pos from) {
    Set<Pos> posSet = new HashSet<>();
    if (!board.isEmpty(from) && board.get(from).getColor() == currentPlayer) {
      for (Pos to : board.get(from).legalMoves()) {
        if (!movePutsCurrentPlayerInCheck(from, to)) {
          posSet.add(to);
        }
      }
    }
    return posSet;
  }

  public boolean movePutsCurrentPlayerInCheck(Pos from, Pos to) {
    board.move(from, to);
    if (board.kingInCheck(currentPlayer)) {
      board.undoMove();
      return true;
    }
    board.undoMove();
    return false;
  }

  public State result() {
    if (!validFroms().isEmpty()) {
      return IN_PROGRESS;
    }
    if (board.kingInCheck(currentPlayer)) {
      switch (currentPlayer) {
        case WHITE:
          return BLACK_WON;
        case BLACK:
          return WHITE_WON;
        default:
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
}
