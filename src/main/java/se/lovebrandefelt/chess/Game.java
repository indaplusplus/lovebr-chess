package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.State.BLACK_WON;
import static se.lovebrandefelt.chess.Game.State.DRAW;
import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Game.State.WHITE_WON;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
    Pos bishopPos = new Pos(0, random.nextInt(4) * 2);
    board.add(new Bishop(WHITE), bishopPos);
    board.add(new Bishop(BLACK), bishopPos.offset(new Pos(7, 0)));
    bishopPos = new Pos(0, random.nextInt(4) * 2 + 1);
    board.add(new Bishop(WHITE), bishopPos);
    board.add(new Bishop(BLACK), bishopPos.offset(new Pos(7, 0)));

    Pos leftRookPos = new Pos(0, random.nextInt(6));
    while (!board.isEmpty(leftRookPos)) {
      leftRookPos = new Pos(0, random.nextInt(6));
    }

    Pos kingPos = new Pos(0, random.nextInt(6 - leftRookPos.getCol()) + leftRookPos.getCol() + 1);
    while (!board.isEmpty(kingPos)) {
      kingPos = new Pos(0, random.nextInt(6 - leftRookPos.getCol()) + leftRookPos.getCol() + 1);
    }

    Pos rightRookPos =
        new Pos(0, (int) (Math.random() * (6 - kingPos.getCol()) + kingPos.getCol() + 1));
    while (!board.isEmpty(rightRookPos)) {
      rightRookPos =
          new Pos(0, (int) (Math.random() * (6 - kingPos.getCol()) + kingPos.getCol() + 1));
    }

    board.add(new Rook(WHITE), leftRookPos);
    board.add(new Rook(BLACK), leftRookPos.offset(new Pos(7, 0)));
    board.add(new King(WHITE), kingPos);
    board.add(new King(BLACK), kingPos.offset(new Pos(7, 0)));
    board.add(new Rook(WHITE), rightRookPos);
    board.add(new Rook(BLACK), rightRookPos.offset(new Pos(7, 0)));

    for (int i = 0; i < 2; i++) {
      Pos knightPos = new Pos(0, random.nextInt(8));
      while (!board.isEmpty(knightPos)) {
        knightPos = new Pos(0, random.nextInt(8));
      }
      board.add(new Knight(WHITE), knightPos);
      board.add(new Knight(BLACK), knightPos.offset(new Pos(7, 0)));
    }

    Pos queenPos = new Pos(0, random.nextInt(8));
    while (!board.isEmpty(queenPos)) {
      queenPos = new Pos(0, random.nextInt(8));
    }
    board.add(new Queen(WHITE), queenPos);
    board.add(new Queen(BLACK), queenPos.offset(new Pos(7, 0)));

    board.addPawnRow(WHITE, 1);
    board.addPawnRow(BLACK, 6);
    return board;
  }

  public Map<Pos, Map<Pos, Move>> legalMoves() {
    Map<Pos, Map<Pos, Move>> legalMoves = new HashMap<>();
    for (int row = 0; row < getBoard().rows(); row++) {
      for (int col = 0; col < getBoard().cols(); col++) {
        Pos from = new Pos(row, col);
        legalMoves.put(from, legalMovesWithCheck(from));
      }
    }
    return legalMoves;
  }

  public Map<Pos, Move> legalMovesWithCheck(Pos from) {
    if (!board.isEmpty(from) && board.get(from).getColor() == currentPlayer) {
      return board
          .get(from)
          .legalMoves()
          .entrySet()
          .stream()
          .filter((entry) -> !movePutsCurrentPlayerInCheck(entry.getValue()))
          .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }
    return new HashMap<>();
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
  }

  public State result() {
    if (!legalMoves().isEmpty()) {
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
