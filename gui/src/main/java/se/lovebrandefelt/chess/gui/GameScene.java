package se.lovebrandefelt.chess.gui;

import static se.lovebrandefelt.chess.Game.standardSetup;

import javafx.scene.Parent;
import javafx.scene.Scene;
import se.lovebrandefelt.chess.Color;
import se.lovebrandefelt.chess.Game;

public class GameScene extends Scene {
  private BoardCanvas canvas;
  private Game game;

  public GameScene(Parent root) {
    super(root);
    canvas = (BoardCanvas) lookup("#board");
    game = new Game(standardSetup(), Color.WHITE);
    canvas.setBoard(game.getBoard());
  }

  public void newGame() {
    game = new Game(standardSetup(), Color.WHITE);
    canvas.setBoard(game.getBoard());
  }
}
