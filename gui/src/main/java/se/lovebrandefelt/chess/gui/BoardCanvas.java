package se.lovebrandefelt.chess.gui;

import static javafx.scene.paint.Color.ANTIQUEWHITE;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.LIGHTSEAGREEN;
import static se.lovebrandefelt.chess.Game.State.IN_PROGRESS;
import static se.lovebrandefelt.chess.Pos.colToString;
import static se.lovebrandefelt.chess.Pos.rowToString;
import static se.lovebrandefelt.chess.gui.GUI.IMAGES;
import static se.lovebrandefelt.chess.gui.GUI.SCENE;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import se.lovebrandefelt.chess.Board;
import se.lovebrandefelt.chess.Pawn;
import se.lovebrandefelt.chess.Piece;
import se.lovebrandefelt.chess.Pos;

public class BoardCanvas extends Canvas {
  private static final Color WHITE_SQUARE_COLOR = ANTIQUEWHITE;
  private static final Color BLACK_SQUARE_COLOR = LIGHTSEAGREEN;
  private static final double squareSize = 75;
  private Board board;
  private Piece selected;

  public void setBoard(Board board) {
    this.board = board;
    setWidth((board.cols() + 2) * squareSize);
    setHeight((board.rows() + 2) * squareSize);
    GUI.PRIMARY_STAGE.sizeToScene();
    selected = null;
  }

  public void draw() {
    if (board != null) {
      GraphicsContext graphicsContext = getGraphicsContext2D();
      graphicsContext.clearRect(0, 0, getWidth(), getHeight());
      for (int row = 0; row < board.rows(); row++) {
        for (int col = 0; col < board.cols(); col++) {
          Pos pos = new Pos(row, col);
          double x = (col + 1) * squareSize;
          double y = getHeight() - ((row + 1) * squareSize) - squareSize;
          if ((row + col) % 2 == 0) {
            graphicsContext.setFill(WHITE_SQUARE_COLOR);
          } else {
            graphicsContext.setFill(BLACK_SQUARE_COLOR);
          }
          graphicsContext.fillRect(x, y, squareSize, squareSize);
          if (selected != null
              && !board.getGame().legalMoves().get(selected.getPos()).containsKey(pos)) {
            graphicsContext.setFill(new Color(0, 0, 0, 0.5));
            graphicsContext.fillRect(x, y, squareSize, squareSize);
          }
          Piece piece = board.get(pos);
          if (piece != null) {
            if (piece == selected) {
              graphicsContext.setFill(new Color(1, 1, 1, 0.5));
              graphicsContext.fillRect(x, y, squareSize, squareSize);
            }
            graphicsContext.drawImage(
                IMAGES.get(piece.getColor()).get(piece.getTypeId()), x, y, squareSize, squareSize);
          }
        }
      }
      graphicsContext.setFill(BLACK);
      graphicsContext.setFont(Font.font(squareSize / 2));
      graphicsContext.setTextAlign(TextAlignment.CENTER);
      graphicsContext.setTextBaseline(VPos.CENTER);
      for (int row = 0; row < board.rows(); row++) {
        double y = getHeight() - (row * squareSize) - (squareSize) * 3 / 2;
        graphicsContext.fillText(rowToString(row), squareSize / 2, y);
        graphicsContext.fillText(rowToString(row), getWidth() - squareSize / 2, y);
      }
      for (int col = 0; col < board.cols(); col++) {
        double x = col * squareSize + (squareSize) * 3 / 2;
        graphicsContext.fillText(colToString(col), x, squareSize / 2);
        graphicsContext.fillText(colToString(col), x, getHeight() - squareSize / 2);
      }
    }
  }

  public void onClick(MouseEvent mouseEvent) {
    if (SCENE.getGame().state() == IN_PROGRESS) {
      int row = board.rows() - (int) (mouseEvent.getY() / squareSize);
      int col = (int) (mouseEvent.getX() / squareSize) - 1;
      Pos pos = new Pos(row, col);
      if (selected == null) {
        if (board.isInsideBounds(pos)
            && !board.isEmpty(pos)
            && board.getGame().legalMoves().containsKey(pos)) {
          selected = board.get(pos);
        }
      } else if (pos.equals(selected.getPos())) {
        selected = null;
      } else if (board.getGame().legalMoves().get(selected.getPos()).containsKey(pos)) {
        String moveString =
            board.getGame().legalMoves().get(selected.getPos()).get(pos).toAlgebraicNotation(board);
        board.getGame().makeMove(selected.getPos(), pos);
        if (board.get(pos) instanceof Pawn) {
          Pawn pawn = (Pawn) board.get(pos);
          if (pawn.canPromote()) {
            List<String> setups = new ArrayList<>();
            setups.add("Bishop");
            setups.add("Knight");
            setups.add("Queen");
            setups.add("Rook");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Queen", setups);
            dialog.setTitle("Promotion");
            dialog.setContentText("Promote into:");
            switch (dialog.showAndWait().orElse("Queen")) {
              case "Bishop":
                pawn.promoteInto('B');
                if (moveString.endsWith("+") || moveString.endsWith("#")) {
                  char last = moveString.charAt(moveString.length() - 1);
                  moveString = moveString.substring(0, moveString.length() - 1) + 'B' + last;
                } else {
                  moveString = moveString + 'B';
                }
                break;
              case "Knight":
                pawn.promoteInto('N');
                if (moveString.endsWith("+") || moveString.endsWith("#")) {
                  char last = moveString.charAt(moveString.length() - 1);
                  moveString = moveString.substring(0, moveString.length() - 1) + 'N' + last;
                } else {
                  moveString = moveString + 'N';
                }
                break;
              case "Queen":
                pawn.promoteInto('Q');
                if (moveString.endsWith("+") || moveString.endsWith("#")) {
                  char last = moveString.charAt(moveString.length() - 1);
                  moveString = moveString.substring(0, moveString.length() - 1) + 'Q' + last;
                } else {
                  moveString = moveString + 'Q';
                }
                break;
              case "Rook":
                pawn.promoteInto('R');
                if (moveString.endsWith("+") || moveString.endsWith("#")) {
                  char last = moveString.charAt(moveString.length() - 1);
                  moveString = moveString.substring(0, moveString.length() - 1) + 'R' + last;
                } else {
                  moveString = moveString + 'R';
                }
                break;
              default:
            }
          }
        }
        System.out.println(moveString);
        selected = null;
      } else if (board.isInsideBounds(pos)
          && !board.isEmpty(pos)
          && board.getGame().legalMoves().containsKey(pos)) {
        selected = board.get(pos);
      }
      GUI.SCENE.update();
    }
  }
}
