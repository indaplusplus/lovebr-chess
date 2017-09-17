package se.lovebrandefelt.chess.console;

import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Game.standardSetup;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import se.lovebrandefelt.chess.Game;

public class PlaybackMain {
  public static void main(String[] args) throws IOException {
    if (args.length > 0) {
      BufferedReader reader =
          Files.newBufferedReader(Paths.get("console/src/main/resources/" + args[0]));
      Game game = new Game(standardSetup(), WHITE);
      String[] line = reader.readLine().split(" ");
      while (!line[0].startsWith("1.")) {
        line = reader.readLine().split(" ");
      }
      while (game.state() == IN_PROGRESS) {
        for (String move : line) {
          if (!move.endsWith(".")) {
            game.makeMove(move);
          }
        }
        line = reader.readLine().split(" ");
      }
      switch (game.state()) {
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
}
