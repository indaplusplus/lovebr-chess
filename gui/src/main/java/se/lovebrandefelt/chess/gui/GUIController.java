package se.lovebrandefelt.chess.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import se.lovebrandefelt.chess.Color;
import se.lovebrandefelt.chess.Pawn;

public class GUIController {
  public Button button;

  public void buttonClicked(ActionEvent actionEvent) {
    button.setText(new Pawn(Color.WHITE).moveDirection() + "");
  }
}
