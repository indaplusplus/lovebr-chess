package se.lovebrandefelt.chess.gui;

import static se.lovebrandefelt.chess.Game.chess960Setup;
import static se.lovebrandefelt.chess.Game.standardSetup;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
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
    List<String> setups = new ArrayList<>();
    setups.add("Standard");
    setups.add("Chess 960");

    ChoiceDialog<String> dialog = new ChoiceDialog<>("Standard", setups);
    dialog.setTitle("Chess - Choose Setup");
    dialog.setContentText("Setup:");
    dialog
        .showAndWait()
        .ifPresent(
            (setup) -> {
              switch (setup) {
                case "Standard":
                  scene.newGame(standardSetup());
                  break;
                case "Chess 960":
                  scene.newGame(chess960Setup());
                  break;
                default:
              }
            });
  }
}
