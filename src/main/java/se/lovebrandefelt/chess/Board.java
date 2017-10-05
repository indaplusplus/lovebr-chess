package se.lovebrandefelt.chess;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Pos.colToString;
import static se.lovebrandefelt.chess.Pos.rowToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
  private Game game;
  private Piece[][] squares;
  private Map<Color, List<Piece>> pieces;
  private LinkedList<String> boardStates;
  private LinkedList<Move> history;

  /**
   * Creates a new board with the specified number of rows and the specified number of columns.
   *
   * @param rows the number of columns
   * @param cols the number of rows
   */
  public Board(int rows, int cols) {
    squares = new Piece[rows][cols];
    pieces = new HashMap<>();
    pieces.put(WHITE, new ArrayList<>());
    pieces.put(BLACK, new ArrayList<>());
    boardStates = new LinkedList<>();
    history = new LinkedList<>();
  }

  /**
   * Returns the number of rows of this board.
   *
   * @return the number of rows
   */
  public int rows() {
    return squares.length;
  }

  /**
   * Returns the number of columns of this board.
   *
   * @return the number of columns
   */
  public int cols() {
    return squares[0].length;
  }

  /**
   * Returns whether the specified position is inside the bounds of this board.
   *
   * @param pos the position to check
   * @return whether the specified position is inside the bounds
   */
  public boolean isInsideBounds(Pos pos) {
    return pos.getRow() >= 0 && pos.getRow() < rows() && pos.getCol() >= 0 && pos.getCol() < cols();
  }

  /**
   * Returns whether the specified position is empty.
   *
   * @param pos the position to check
   * @return whether the specified position is empty
   */
  public boolean isEmpty(Pos pos) {
    return get(pos) == null;
  }

  /**
   * Returns the piece at the specified position.
   *
   * @param pos the position to get the piece at
   * @return the piece at the specified position
   */
  public Piece get(Pos pos) {
    return squares[pos.getRow()][pos.getCol()];
  }

  /**
   * Returns whether the specified position is threatened by the specified color.
   *
   * @param pos the position to check
   * @param by the color to check for
   * @return whether the specified position is threatened by the specified color
   */
  public boolean isThreatened(Pos pos, Color by) {
    return pieces
        .get(by)
        .stream()
        .anyMatch((piece -> piece.recursionSafeLegalMoves().containsKey(pos)));
  }

  /**
   * Returns whether the king of the specified color is in check.
   *
   * @param color the color to check
   * @return whether the king of the specified color is in check
   */
  public boolean kingInCheck(Color color) {
    return pieces
        .get(color)
        .stream()
        .anyMatch(
            (piece) -> piece.getTypeId() == 'K' && isThreatened(piece.getPos(), color.next()));
  }

  /**
   * Adds the specified piece at the specified position.
   *
   * @param piece the piece to add
   * @param pos the position to add the piece at
   * @return the added piece
   */
  public Piece add(Piece piece, Pos pos) {
    if (!isEmpty(pos)) {
      pieces.get(get(pos).getColor()).remove(get(pos));
    }
    squares[pos.getRow()][pos.getCol()] = piece;
    piece.setBoard(this);
    piece.setPos(pos);
    pieces.get(piece.getColor()).add(piece);
    return piece;
  }

  /**
   * Fills the specified row with pawns of the specified color.
   *
   * @param row the row to fill
   * @param color the color of the pawns
   */
  public void addPawnRow(int row, Color color) {
    for (int i = 0; i < cols(); i++) {
      add(new Pawn(color), new Pos(row, i));
    }
  }

  /**
   * Removes the piece at the specified position.
   *
   * @param pos the position to remove the piece at
   * @return the removed piece
   */
  public Piece remove(Pos pos) {
    Piece piece = get(pos);
    if (piece != null) {
      pieces.get(piece.getColor()).remove(piece);
    }
    squares[pos.getRow()][pos.getCol()] = null;
    return piece;
  }

  /**
   * Performs the specified move.
   *
   * @param move the move to perform
   */
  public void move(Move move) {
    move.perform(this);
    boardStates.push(toString());
    history.push(move);
  }

  /** Undoes the last move. */
  public void undoMove() {
    boardStates.pop();
    history.pop().undo(this);
  }

  public Move algebraicNotationToMove(String moveString) {
    if (moveString.startsWith("O-O")) {
      Stream<Pos> fromCanditates =
          game.legalMoves().keySet().stream().filter((from) -> get(from).getTypeId() == 'K');
      if (fromCanditates.count() != 1) {
        throw new IllegalArgumentException();
      }
      Pos from = fromCanditates.findFirst().orElseThrow(IllegalArgumentException::new);
      Pos to = moveString.equals("O-O") ? new Pos(from.getRow(), 6) : new Pos(from.getRow(), 2);
      return game.legalMoves().get(from).get(to);
    } else {
      moveString = moveString.replace("x", "").replace("+", "").replace("#", "");
      char typeId;
      String fromString;
      Pos to;
      if (Character.isUpperCase(moveString.charAt(0))) {
        typeId = moveString.charAt(0);
        fromString = moveString.substring(1, moveString.length() - 2);
        to = new Pos(moveString.substring(moveString.length() - 2));
      } else {
        typeId = 'P';
        if (Character.isUpperCase(moveString.charAt(moveString.length() - 1))) {
          fromString = moveString.substring(0, moveString.length() - 3);
          to = new Pos(moveString.substring(moveString.length() - 3, moveString.length() - 1));
        } else {
          fromString = moveString.substring(0, moveString.length() - 2);
          to = new Pos(moveString.substring(moveString.length() - 2));
        }
      }
      List<Pos> fromCandidates =
          game.legalMoves()
              .keySet()
              .stream()
              .filter(
                  (from) ->
                      get(from).getTypeId() == typeId
                          && game.legalMoves().get(from).containsKey(to))
              .collect(Collectors.toList());
      if (fromString.length() == 1) {
        if (Character.isDigit(fromString.charAt(0))) {
          fromCandidates =
              fromCandidates
                  .stream()
                  .filter((fromCandidate) -> rowToString(fromCandidate.getRow()).equals(fromString))
                  .collect(Collectors.toList());
        } else {
          fromCandidates =
              fromCandidates
                  .stream()
                  .filter((fromCandidate) -> colToString(fromCandidate.getCol()).equals(fromString))
                  .collect(Collectors.toList());
        }
      } else if (fromString.length() == 2) {
        fromCandidates =
            fromCandidates
                .stream()
                .filter((fromCandidate) -> fromCandidate.toString().equals(fromString))
                .collect(Collectors.toList());
      }
      if (fromCandidates.size() != 1) {
        throw new IllegalArgumentException();
      }
      Pos from = fromCandidates.get(0);
      if (game.legalMoves().get(from).get(to) instanceof PromotionMove) {
        ((PromotionMove) game.legalMoves().get(from).get(to))
            .setPromoteInto(moveString.charAt(moveString.length() - 1));
      }
      return game.legalMoves().get(from).get(to);
    }
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public Map<Color, List<Piece>> getPieces() {
    return pieces;
  }

  public LinkedList<String> getBoardStates() {
    return boardStates;
  }

  public LinkedList<Move> getHistory() {
    return history;
  }

  public String toForsythEdwardsNotation() {
    StringBuilder builder = new StringBuilder();
    for (int row = rows() - 1; row >= 0; row--) {
      for (int col = 0; col < cols(); col++) {
        int tempCol = col;
        while (isInsideBounds(new Pos(row, tempCol)) && isEmpty(new Pos(row, tempCol))) {
          tempCol++;
        }
        if (tempCol > col) {
          builder.append(tempCol - col);
          col = tempCol;
        } else if (get(new Pos(row, col)).getColor() == WHITE) {
          builder.append(get(new Pos(row, col)).getTypeId());
        } else {
          builder.append(Character.toLowerCase(get(new Pos(row, col)).getTypeId()));
        }
      }
      if (row > 0) {
        builder.append('/');
      }
    }
    builder.append(' ');
    builder.append(Character.toLowerCase(getGame().getCurrentPlayer().toString().charAt(0)));
    String castlingString = "";
    Piece whiteKing =
        pieces.get(WHITE).stream().filter((piece) -> piece.getTypeId() == 'K').findFirst().get();
    Map<Pos, Map<Pos, Move>> whiteLegalMoves = getGame().legalMovesForPlayer(WHITE);
    Piece blackKing =
        pieces.get(BLACK).stream().filter((piece) -> piece.getTypeId() == 'K').findFirst().get();
    Map<Pos, Map<Pos, Move>> blackLegalMoves = getGame().legalMovesForPlayer(BLACK);
    if (whiteLegalMoves.containsKey(whiteKing.getPos())) {
      List<Move> castlingMoves =
          whiteLegalMoves
              .get(whiteKing.getPos())
              .values()
              .stream()
              .filter(move -> move instanceof CastlingMove)
              .collect(Collectors.toList());
      if (castlingMoves.size() == 2) {
        castlingString += "KQ";
      } else if (castlingMoves.size() == 1) {
        if (castlingMoves.get(0).getTo().equals(new Pos("g1"))) {
          castlingString += "K";
        } else {
          castlingString += "Q";
        }
      }
    }
    if (blackLegalMoves.containsKey(blackKing.getPos())) {
      List<Move> castlingMoves =
          blackLegalMoves
              .get(blackKing.getPos())
              .values()
              .stream()
              .filter(move -> move instanceof CastlingMove)
              .collect(Collectors.toList());
      if (castlingMoves.size() == 2) {
        castlingString += "kq";
      } else if (castlingMoves.size() == 1) {
        if (castlingMoves.get(0).getTo().equals(new Pos("g1"))) {
          castlingString += "k";
        } else {
          castlingString += "q";
        }
      }
    }
    builder.append(' ');
    if (castlingString.isEmpty()) {
      builder.append('-');
    } else {
      builder.append(castlingString);
    }
    builder.append(' ');
    Move lastMove = getHistory().peek();
    if (lastMove.getPiece().getTypeId() == 'P'
        && lastMove.getTo().getRow() - lastMove.getFrom().getRow()
        == ((Pawn) lastMove.getPiece()).moveDirection() * 2) {
      builder.append(
          lastMove.getTo().offset(new Pos(-((Pawn) lastMove.getPiece()).moveDirection(), 0)));
    } else {
      builder.append('-');
    }
    builder.append(' ');
    int halfMoves;
    for (halfMoves = 0;
         halfMoves < getHistory().size()
             && getHistory().get(halfMoves).getPiece().getTypeId() != 'P'
             && getHistory().get(halfMoves).getCaptured() == null;
         halfMoves++) {
    }
    builder.append(halfMoves);
    builder.append(' ');
    builder.append(getHistory().size() / 2);
    return builder.toString();
  }

  public Color extractCurrentPlayer(String forsythEdwardsNotation) {
    if (forsythEdwardsNotation.split(" ")[1].equals("w")) {
      return WHITE;
    }
    return BLACK;
  }

  @Override
  public String toString() {
    StringBuilder boardStringBuilder = new StringBuilder();
    for (int row = 0; row < rows(); row++) {
      for (int col = 0; col < cols(); col++) {
        Piece piece = get(new Pos(row, col));
        if (piece == null) {
          boardStringBuilder.append(' ');
        } else {
          boardStringBuilder.append(piece.getColor().toString().charAt(0) + piece.getTypeId());
        }
      }
    }
    return boardStringBuilder.toString();
  }
}
