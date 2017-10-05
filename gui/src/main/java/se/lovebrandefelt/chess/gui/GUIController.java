package se.lovebrandefelt.chess.gui;

import static se.lovebrandefelt.chess.Color.BLACK;
import static se.lovebrandefelt.chess.Color.WHITE;
import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Game.chess960Setup;
import static se.lovebrandefelt.chess.Game.silvermanChessSetup;
import static se.lovebrandefelt.chess.Game.standardSetup;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import se.kth.inda17plusplus.MoveOuterClass;
import se.lovebrandefelt.chess.Color;
import se.lovebrandefelt.chess.Move;

public class GUIController {
  public ToolBar tools;
  public Button newGame;
  public Button newOnlineGame;
  public Button joinOnlineGame;
  public BoardCanvas board;
  private ServerSocket server;
  private Socket socket;
  private ExecutorService executorService;
  private Color you;

  public GUIController() throws IOException {
    executorService = Executors.newSingleThreadExecutor();
  }

  public void canvasClicked(MouseEvent mouseEvent) throws IOException {
    if (you == null || you == board.getBoard().getGame().getCurrentPlayer()) {
      if (board.onClick(mouseEvent) && socket != null && socket.isConnected()) {
        MoveOuterClass.Move serverMove =
            MoveOuterClass.Move.newBuilder()
                .setMove(board.getBoard().getHistory().peek().getAlgebraicNotation())
                .setResultingState(board.getBoard().toForsythEdwardsNotation())
                .build();
        serverMove.writeDelimitedTo(socket.getOutputStream());
      }
      GUI.SCENE.update();
    }
  }

  public void newGame(ActionEvent actionEvent) throws IOException {
    GameScene scene = (GameScene) newGame.getScene();

    List<String> setups = new ArrayList<>();
    setups.add("Standard");
    setups.add("Chess 960");
    setups.add("Silverman 4x5");
    ChoiceDialog<String> dialog = new ChoiceDialog<>("Standard", setups);
    dialog.setTitle("Choose Setup");
    dialog.setContentText("Setup:");
    dialog
        .showAndWait()
        .ifPresent(
            setup -> {
              switch (setup) {
                case "Standard":
                  scene.newGame(standardSetup());
                  break;
                case "Chess 960":
                  scene.newGame(chess960Setup());
                  break;
                case "Silverman 4x5":
                  scene.newGame(silvermanChessSetup());
                  break;
                default:
              }
            });
    if (socket != null) {
      socket.close();
    }
    if (server != null) {
      server.close();
    }
    you = null;
  }

  public void newOnlineGame(ActionEvent actionEvent) throws IOException {
    if (socket == null || socket.isClosed()) {
      CompletableFuture.runAsync(
          () -> {
            try {
              server = new ServerSocket(0xDAD);
              socket = server.accept();
            } catch (IOException ignored) {
            }
          })
          .thenAccept(
              (v) -> {
                Platform.runLater(() -> ((GameScene) newGame.getScene()).newGame(standardSetup()));
                you = WHITE;
                executorService.submit(() -> readMovesFromSocket(socket));
              });
    }
  }

  public void joinOnlineGame(ActionEvent actionEvent) {
    GameScene scene = (GameScene) newGame.getScene();

    TextInputDialog dialog = new TextInputDialog("127.0.0.1");
    dialog.setTitle("Enter IP Address");
    dialog.setContentText("IP Address:");
    dialog
        .showAndWait()
        .ifPresent(
            ip -> {
              try {
                if (socket != null) {
                  socket.close();
                }
                if (server != null) {
                  server.close();
                }
                socket = new Socket(ip, 0xDAD);
                ((GameScene) newGame.getScene()).newGame(standardSetup());
                executorService.submit(
                    () -> {
                      readMovesFromSocket(socket);
                    });
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
  }

  void update(GameScene scene) {
    StringBuilder title = new StringBuilder("Chess - ");
    if (socket != null && socket.isConnected()) {
      switch (scene.getGame().state()) {
        case IN_PROGRESS:
          if (scene.getGame().getCurrentPlayer() == you) {
            title.append("Your Turn");
          } else {
            title.append(scene.getGame().getCurrentPlayer());
            title.append("'s Turn");
          }
          if (scene.getGame().getBoard().kingInCheck(scene.getGame().getCurrentPlayer())) {
            title.append(" - In Check");
          }
          break;
        case WHITE_WON:
          if (WHITE == you) {
            title.append("You Won");
          } else {
            title.append("You Lost");
          }
          break;
        case BLACK_WON:
          if (BLACK == you) {
            title.append("You Won");
          } else {
            title.append("You Lost");
          }
          break;
        case DRAW:
          title.append("DRAW");
          break;
        default:
      }
      if (scene.getGame().state() != IN_PROGRESS) {
        try {
          if (socket != null) {
            socket.close();
          }
          if (server != null) {
            server.close();
          }
        } catch (IOException ignored) {
        }
        you = null;
      }
    } else {
      switch (scene.getGame().state()) {
        case IN_PROGRESS:
          title.append(scene.getGame().getCurrentPlayer());
          title.append("'s Turn");
          if (scene.getGame().getBoard().kingInCheck(scene.getGame().getCurrentPlayer())) {
            title.append(" - In Check");
          }
          break;
        case WHITE_WON:
          title.append("WHITE Won");
          break;
        case BLACK_WON:
          title.append("BLACK Won");
          break;
        case DRAW:
          title.append("DRAW");
          break;
        default:
      }
    }
    GUI.PRIMARY_STAGE.setTitle(title.toString());
  }

  public void readMovesFromSocket(Socket socket) {
    while (socket != null && socket.isConnected()) {
      try {
        MoveOuterClass.Move serverMove =
            MoveOuterClass.Move.parseDelimitedFrom(socket.getInputStream());
        System.out.println(serverMove.getMove());
        System.out.println(
            board
                .getBoard()
                .getGame()
                .legalMoves()
                .values()
                .stream()
                .flatMap(map -> map.values().stream())
                .collect(Collectors.toSet()));
        Move move = board.getBoard().algebraicNotationToMove(serverMove.getMove());
        you = board.getBoard().extractCurrentPlayer(serverMove.getResultingState());
        board.getBoard().getGame().makeMove(move.getFrom(), move.getTo());
        Platform.runLater(GUI.SCENE::update);
      } catch (IOException ignored) {
      }
    }
  }
}
