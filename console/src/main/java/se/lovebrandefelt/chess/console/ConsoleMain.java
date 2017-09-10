package se.lovebrandefelt.chess.console;

import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.defaultSetup;

import se.lovebrandefelt.chess.Game;
import se.lovebrandefelt.chess.Pos;

public class ConsoleMain {
  public static void main(String[] args) {
    Game game = new Game(defaultSetup(), WHITE);
    game.makeMove(new Pos("E2"), new Pos("E3"));
    game.makeMove(new Pos("B8"), new Pos("C6"));
    System.out.println(game.getBoard());
  }
}
