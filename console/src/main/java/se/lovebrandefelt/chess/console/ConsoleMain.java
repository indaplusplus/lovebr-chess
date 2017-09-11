package se.lovebrandefelt.chess.console;

import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.defaultSetup;

import java.util.Scanner;
import se.lovebrandefelt.chess.Game;
import se.lovebrandefelt.chess.Pos;

public class ConsoleMain {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Game game = new Game(defaultSetup(), WHITE);
    while (true) {
      System.out.println(game.getBoard());
      System.out.println(game.getCurrentPlayer() + "'s turn");
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
        if (game.validFroms().contains(from)) {
          break;
        }
        System.out.println("You don't have a piece on that square!");
      }
      while (true) {
        System.out.print("Move to: ");
        try {
          to = new Pos(scanner.next());
        } catch (IllegalArgumentException e) {
          System.out.println("That is not a square!");
          continue;
        }
        if (game.getBoard().get(from).legalMoves().contains(to)) {
          break;
        }
        System.out.println("That move is not legal!");
      }
      game.makeMove(from, to);
    }
  }
}
