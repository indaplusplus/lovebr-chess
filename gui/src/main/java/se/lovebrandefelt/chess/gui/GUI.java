package se.lovebrandefelt.chess.gui;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.lovebrandefelt.chess.Color;

public class GUI extends Application {
  public static final Map<Color, Map<Character, Image>> IMAGES = new HashMap<>();
  public static Stage PRIMARY_STAGE;
  public static GUIController CONTROLLER;
  public static GameScene SCENE;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void init() {
    IMAGES.put(WHITE, new HashMap<>());
    IMAGES.get(WHITE).put('B', new Image("WHITE_B.png", 75, 75, true, true));
    IMAGES.get(WHITE).put('K', new Image("WHITE_K.png", 75, 75, true, true));
    IMAGES.get(WHITE).put('N', new Image("WHITE_N.png", 75, 75, true, true));
    IMAGES.get(WHITE).put('P', new Image("WHITE_P.png", 75, 75, true, true));
    IMAGES.get(WHITE).put('Q', new Image("WHITE_Q.png", 75, 75, true, true));
    IMAGES.get(WHITE).put('R', new Image("WHITE_R.png", 75, 75, true, true));
    IMAGES.put(BLACK, new HashMap<>());
    IMAGES.get(BLACK).put('B', new Image("BLACK_B.png", 75, 75, true, true));
    IMAGES.get(BLACK).put('K', new Image("BLACK_K.png", 75, 75, true, true));
    IMAGES.get(BLACK).put('N', new Image("BLACK_N.png", 75, 75, true, true));
    IMAGES.get(BLACK).put('P', new Image("BLACK_P.png", 75, 75, true, true));
    IMAGES.get(BLACK).put('Q', new Image("BLACK_Q.png", 75, 75, true, true));
    IMAGES.get(BLACK).put('R', new Image("BLACK_R.png", 75, 75, true, true));
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
    PRIMARY_STAGE = primaryStage;
    Parent root = fxmlLoader.load();
    CONTROLLER = fxmlLoader.getController();
    SCENE = new GameScene(root);
    primaryStage.setScene(SCENE);
    primaryStage.show();
    primaryStage.sizeToScene();
  }
}
