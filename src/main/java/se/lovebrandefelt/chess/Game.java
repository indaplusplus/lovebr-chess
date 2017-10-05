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

  /**
   * Creates a new game using the specified setup with the specified starting player.
   *
   * @param setup the setup to use
   * @param startingPlayer the starting player
   */
  public Game(Board setup, Color startingPlayer) {
    board = setup;
    board.setGame(this);
    currentPlayer = startingPlayer;
    legalMoves = new HashMap<>();
  }

  /**
   * Returns a board with the standard chess setup.
   *
   * @return a board with the standard chess setup
   */
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
    board.addPawnRow(1, WHITE);
    board.add(new Rook(BLACK), new Pos(7, 0));
    board.add(new Knight(BLACK), new Pos(7, 1));
    board.add(new Bishop(BLACK), new Pos(7, 2));
    board.add(new Queen(BLACK), new Pos(7, 3));
    board.add(new King(BLACK), new Pos(7, 4));
    board.add(new Bishop(BLACK), new Pos(7, 5));
    board.add(new Knight(BLACK), new Pos(7, 6));
    board.add(new Rook(BLACK), new Pos(7, 7));
    board.addPawnRow(6, BLACK);
    return board;
  }

  /**
   * Returns a board with a random Chess 960 setup.
   *
   * @return a board with a random Chess 960 setup
   */
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
    board.addPawnRow(1, WHITE);
    board.addPawnRow(6, BLACK);
    return board;
  }

  /**
   * Returns a board with the Silverman 4x4 setup.
   *
   * @return a board with the Silverman 4x4 setup
   */
  public static Board silvermanChessSetup() {
    Board board = new Board(5, 4);
    board.add(new Rook(WHITE), new Pos(0, 0));
    board.add(new Queen(WHITE), new Pos(0, 1));
    board.add(new SilvermanKing(WHITE), new Pos(0, 2));
    board.add(new Rook(WHITE), new Pos(0, 3));
    board.addPawnRow(1, WHITE);

    board.add(new Rook(BLACK), new Pos(4, 0));
    board.add(new Queen(BLACK), new Pos(4, 1));
    board.add(new SilvermanKing(BLACK), new Pos(4, 2));
    board.add(new Rook(BLACK), new Pos(4, 3));
    board.addPawnRow(3, BLACK);
    return board;
  }

  /**
   * Returns a map where each key is a position the current player can move from and each value a
   * map where each key is a position that piece can move to and each value is a corresponding Move
   * object.
   *
   * @return a map of legal moves
   */
  public Map<Pos, Map<Pos, Move>> legalMoves() {
    if (legalMoves.isEmpty()) {
      new ArrayList<>(getBoard().getPieces().get(currentPlayer))
          .forEach((piece) -> legalMoves.put(piece.getPos(), legalMovesWithCheck(piece.getPos())));
    }
    return legalMoves;
  }

  public Map<Pos, Map<Pos, Move>> legalMovesForPlayer(Color player) {
    Map<Pos, Map<Pos, Move>> legalMovesForPlayer = new HashMap<>();
    new ArrayList<>(getBoard().getPieces().get(player))
        .forEach((piece) -> legalMovesForPlayer.put(piece.getPos(), legalMovesWithCheck(piece.getPos())));
    return legalMovesForPlayer;
  }

  private Map<Pos, Move> legalMovesWithCheck(Pos from) {
    return board
        .get(from)
        .legalMoves()
        .entrySet()
        .stream()
        .filter((entry) -> !movePutsCurrentPlayerInCheck(entry.getValue()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  private boolean movePutsCurrentPlayerInCheck(Move move) {
    board.move(move);
    if (board.kingInCheck(currentPlayer)) {
      board.undoMove();
      return true;
    }
    board.undoMove();
    return false;
  }

  /**
   * Makes a move from one position to another, if the move is legal.
   *
   * @param from the position to move from
   * @param to the position to move to
   */
  public void makeMove(Pos from, Pos to) {
    if (state() == IN_PROGRESS) {
      Move move = legalMoves().get(from).get(to);
      if (move != null) {
        board.move(move);
        currentPlayer = currentPlayer.next();
      }
      legalMoves = new HashMap<>();
    }
  }

  void undoMove() {
    board.undoMove();
    currentPlayer = currentPlayer.next();
    legalMoves = new HashMap<>();
  }

  /**
   * Returns the current state of the game.
   *
   * @return the current state of the game
   */
  public State state() {
    // Fifty moves rule
    if (board.getHistory().size() >= 100
        && board
            .getHistory()
        .subList(0, 100)
            .stream()
            .allMatch((move) -> move.getPiece().getTypeId() != 'P' && move.getCaptured() == null)) {
      return DRAW;
    }

    // Recurring board state rule
    Map<String, Integer> boardStates = new HashMap<>();
    for (int i = 0;
         i < board.getHistory().size()
            && board.getHistory().get(i).getCaptured() == null
            && !(board.getHistory().get(i) instanceof CastlingMove);
         i++) {
      boardStates.put(
          board.getBoardStates().get(i),
          boardStates.getOrDefault(board.getBoardStates().get(i), 0) + 1);
    }
    if (boardStates.values().stream().anyMatch((occurrences) -> occurrences >= 3)) {
      return DRAW;
    }

    // Check if checkmate is impossible
    String whitePieces =
        board
            .getPieces()
            .get(WHITE)
            .stream()
            .map(Piece::getTypeId)
            .sorted()
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    String blackPieces =
        board
            .getPieces()
            .get(BLACK)
            .stream()
            .map(Piece::getTypeId)
            .sorted()
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    if ((whitePieces.equals("K") && blackPieces.equals("K"))
        || (whitePieces.equals("BK") && blackPieces.equals("K"))
        || (whitePieces.equals("K") && blackPieces.equals("BK"))
        || (whitePieces.equals("KN") && blackPieces.equals("K"))
        || (whitePieces.equals("K") && blackPieces.equals("KN"))) {
      return DRAW;
    }
    if (whitePieces.equals("BK") && blackPieces.equals("BK")) {
      Pos whiteBishopPos =
          board
              .getPieces()
              .get(WHITE)
              .stream()
              .filter((piece) -> piece.getTypeId() == 'B')
              .findFirst()
              .get()
              .getPos();
      Pos blackBishopPos =
          board
              .getPieces()
              .get(BLACK)
              .stream()
              .filter((piece) -> piece.getTypeId() == 'B')
              .findFirst()
              .get()
              .getPos();
      if ((whiteBishopPos.getRow()
                  + whiteBishopPos.getCol()
                  + blackBishopPos.getRow()
                  + blackBishopPos.getCol())
              % 2
          == 0) {
        return DRAW;
      }
    }

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
