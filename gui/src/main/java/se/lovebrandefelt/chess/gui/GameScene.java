package se.lovebrandefelt.chess.gui;

import static se.lovebrandefelt.chess.Game.standardSetup;

import javafx.scene.Parent;
import javafx.scene.Scene;
import se.lovebrandefelt.chess.Board;
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
    update();
  }

  public void newGame(Board setup) {
    game = new Game(setup, Color.WHITE);
    canvas.setBoard(game.getBoard());
    update();
  }

  public void update() {
    canvas.draw();
    switch (game.state()) {
      case IN_PROGRESS:
        if (game.getBoard().kingInCheck(game.getCurrentPlayer())) {
          GUI.PRIMARY_STAGE.setTitle("Chess - " + game.getCurrentPlayer() + "'s turn - In Check");
        } else {
          GUI.PRIMARY_STAGE.setTitle("Chess - " + game.getCurrentPlayer() + "'s turn");
        }
        break;
      case WHITE_WON:
        GUI.PRIMARY_STAGE.setTitle("Chess - WHITE Won");
        break;
      case BLACK_WON:
        GUI.PRIMARY_STAGE.setTitle("Chess - BLACK Won");
        break;
      case DRAW:
        GUI.PRIMARY_STAGE.setTitle("Chess - DRAW");
        break;
      default:
    }
  }
}
