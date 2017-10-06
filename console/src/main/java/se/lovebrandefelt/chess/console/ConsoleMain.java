package se.lovebrandefelt.chess.console;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Game.chess960Setup;
import static se.lovebrandefelt.chess.Game.standardSetup;

import java.util.Scanner;
import se.lovebrandefelt.chess.Board;
import se.lovebrandefelt.chess.Game;
import se.lovebrandefelt.chess.Move;
import se.lovebrandefelt.chess.Pos;
import se.lovebrandefelt.chess.PromotionMove;

public class ConsoleMain {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Game game;
    if (args.length > 0) {
      if (args[0].equals("960")) {
        game = new Game(chess960Setup(), WHITE);
      } else {
        game = new Game(standardSetup(), WHITE);
      }
    } else {
      game = new Game(standardSetup(), WHITE);
    }
    while (game.getState() == IN_PROGRESS) {
      System.out.println(boardToString(game.getBoard()));
      if (game.getBoard().kingInCheck(game.getCurrentPlayer())) {
        System.out.println(game.getCurrentPlayer() + "'s turn - In Check");
      } else {
        System.out.println(game.getCurrentPlayer() + "'s turn");
      }
      while (true) {
        System.out.print("Enter your move: ");
        try {
          String moveString = scanner.next();
          Move move = game.getBoard().algebraicNotationToMove(moveString);
          if (!(move instanceof PromotionMove) || ((PromotionMove) move).getPromoteInto() != null) {
            game.makeMove(move.getFrom(), move.getTo());
            break;
          }
          System.out.println("That move is not legal!");
        } catch (IllegalArgumentException ignored) {
          System.out.println("That move is not legal!");
        }
      }
    }
    switch (game.getState()) {
      case WHITE_WON:
        System.out.println("WHITE won");
        break;
      case BLACK_WON:
        System.out.println("BLACK won");
        break;
      case DRAW:
        System.out.println("DRAW");
        break;
      default:
    }
  }

  /**
   * Returns a string representation of the specified board.
   *
   * @param board the board
   * @return a string representation of the specified board
   */
  public static String boardToString(Board board) {
    StringBuilder stringBuilder = new StringBuilder("");
    StringBuilder colStringBuilder = new StringBuilder("  ");
    for (int col = 0; col < board.cols(); col++) {
      colStringBuilder.append(String.format("%-2s", Pos.colToString(col)));
    }
    colStringBuilder.append("  ");
    stringBuilder.append(colStringBuilder);
    stringBuilder.append("Captures: ");
    board
        .getHistory()
        .stream()
        .filter((move) -> move.getCaptured() != null && move.getCaptured().getColor() == WHITE)
        .map((move) -> move.getCaptured().getTypeId())
        .sorted()
        .forEach((typeId) -> stringBuilder.append(typeId).append(' '));
    stringBuilder.append('\n');
    for (int row = board.rows() - 1; row >= 0; row--) {
      String rowString = String.format("%-2s", Pos.rowToString(row));
      stringBuilder.append(rowString);
      for (int col = 0; col < board.cols(); col++) {
        Pos pos = new Pos(row, col);
        if (board.isEmpty(pos)) {
          stringBuilder.append("- ");
        } else {
          if (board.get(pos).getColor() == WHITE) {
            stringBuilder.append(board.get(pos).getTypeId()).append(" ");
          } else {
            stringBuilder.append(Character.toLowerCase(board.get(pos).getTypeId())).append(" ");
          }
        }
      }
      stringBuilder.append(rowString);
      stringBuilder.append('\n');
    }
    stringBuilder.append(colStringBuilder.toString().toLowerCase());
    stringBuilder.append("Captures: ");
    board
        .getHistory()
        .stream()
        .filter((move) -> move.getCaptured() != null && move.getCaptured().getColor() == BLACK)
        .map((move) -> move.getCaptured().getTypeId())
        .sorted()
        .forEach((typeId) -> stringBuilder.append(typeId).append(' '));
    return stringBuilder.toString();
  }
}
