package se.lovebrandefelt.chess.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;

public class GUIController {
  public ToolBar tools;
  public Button newGame;
  public BoardCanvas board;

  public void canvasClicked(MouseEvent mouseEvent) {
    board.onClick(mouseEvent);
  }

  public void newGame(ActionEvent actionEvent) {
    GameScene scene = (GameScene) newGame.getScene();
    scene.newGame();
  }
}
