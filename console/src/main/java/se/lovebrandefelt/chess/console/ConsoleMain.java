package se.lovebrandefelt.chess.console;

import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Game.chess960Setup;
import static se.lovebrandefelt.chess.Game.standardSetup;

import java.util.Scanner;
import se.lovebrandefelt.chess.Bishop;
import se.lovebrandefelt.chess.Game;
import se.lovebrandefelt.chess.Knight;
import se.lovebrandefelt.chess.Pawn;
import se.lovebrandefelt.chess.Piece;
import se.lovebrandefelt.chess.Pos;
import se.lovebrandefelt.chess.Queen;
import se.lovebrandefelt.chess.Rook;

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
    while (game.result() == IN_PROGRESS) {
      System.out.println(game.getBoard());
      if (game.getBoard().kingInCheck(game.getCurrentPlayer())) {
        System.out.println(game.getCurrentPlayer() + "'s turn - In Check");
      } else {
        System.out.println(game.getCurrentPlayer() + "'s turn");
      }
      Pos from;
      Pos to;
      while (true) {
        System.out.print("Move from: ");
        try {
          from = new Pos(scanner.next());
        } catch (IllegalArgumentException e) {
          System.out.println("That is not a square!");
          continue;
        }
        if (!game.legalMoves().get(from).isEmpty()) {
          break;
        }
        System.out.println("You can't move from there!");
      }
      while (true) {
        System.out.print("Move to: ");
        try {
          to = new Pos(scanner.next());
        } catch (IllegalArgumentException e) {
          System.out.println("That is not a square!");
          continue;
        }
        if (game.legalMoves().get(from).containsKey(to)) {
          break;
        }
        System.out.println("That move is not legal!");
      }
      game.makeMove(from, to);
      Piece piece = game.getBoard().get(to);
      if (piece.getTypeId() == 'P' && ((Pawn)piece).canPromote()) {
        String promoteInto;
        loop: while (true) {
          System.out.print("Promote into: ");
          promoteInto = scanner.next();
          if (promoteInto.length() == 1) {
            switch (promoteInto.charAt(0)) {
              case 'B':
                ((Pawn)piece).promote(new Bishop(piece.getColor()));
                break loop;
              case 'N':
                ((Pawn) piece).promote(new Knight(piece.getColor()));
                break loop;
              case 'R':
                ((Pawn)piece).promote(new Rook(piece.getColor()));
                break loop;
              case 'Q':
                ((Pawn)piece).promote(new Queen(piece.getColor()));
                break loop;
              default:
            }
          }
          System.out.println("You can't promote into that!");
        }
      }
    }
    switch (game.result()) {
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
}
