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
  public static GameScene SCENE;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void init() {
    IMAGES.put(WHITE, new HashMap<>());
    IMAGES.get(WHITE).put('B', new Image("WHITE_B.png"));
    IMAGES.get(WHITE).put('K', new Image("WHITE_K.png"));
    IMAGES.get(WHITE).put('N', new Image("WHITE_N.png"));
    IMAGES.get(WHITE).put('P', new Image("WHITE_P.png"));
    IMAGES.get(WHITE).put('Q', new Image("WHITE_Q.png"));
    IMAGES.get(WHITE).put('R', new Image("WHITE_R.png"));
    IMAGES.put(BLACK, new HashMap<>());
    IMAGES.get(BLACK).put('B', new Image("BLACK_B.png"));
    IMAGES.get(BLACK).put('K', new Image("BLACK_K.png"));
    IMAGES.get(BLACK).put('N', new Image("BLACK_N.png"));
    IMAGES.get(BLACK).put('P', new Image("BLACK_P.png"));
    IMAGES.get(BLACK).put('Q', new Image("BLACK_Q.png"));
    IMAGES.get(BLACK).put('R', new Image("BLACK_R.png"));
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    GUI.PRIMARY_STAGE = primaryStage;
    Parent root = FXMLLoader.load(getClass().getResource("/GUI.fxml"));
    SCENE = new GameScene(root);
    primaryStage.setScene(SCENE);
    primaryStage.show();
    primaryStage.sizeToScene();
  }
}
