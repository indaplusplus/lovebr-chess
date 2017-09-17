package se.lovebrandefelt.chess.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class GUIController {
  public void buttonClicked(ActionEvent actionEvent) {
    ((Button) actionEvent.getSource()).setText("Button clicked!");
  }
}
