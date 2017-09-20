package se.lovebrandefelt.chess.gui;

import static javafx.scene.paint.Color.ANTIQUEWHITE;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.LIGHTSEAGREEN;
import static se.lovebrandefelt.chess.Pos.colToString;
import static se.lovebrandefelt.chess.Pos.rowToString;
import static se.lovebrandefelt.chess.gui.GUI.IMAGES;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import se.lovebrandefelt.chess.Board;
import se.lovebrandefelt.chess.Piece;
import se.lovebrandefelt.chess.Pos;

public class BoardCanvas extends Canvas {
  private static final Color WHITE_SQUARE_COLOR = ANTIQUEWHITE;
  private static final Color BLACK_SQUARE_COLOR = LIGHTSEAGREEN;
  private Board board;
  private double squareSize;
  private Piece selected;

  public void setBoard(Board board) {
    this.board = board;
    squareSize = getWidth() / (board.cols() + 2);
    selected = null;
    draw();
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
      for (int col = 0; col < board.rows(); col++) {
        double x = col * squareSize + (squareSize) * 3 / 2;
        graphicsContext.fillText(colToString(col), x, squareSize / 2);
        graphicsContext.fillText(colToString(col), x, getHeight() - squareSize / 2);
      }
    }
  }

  public void onClick(MouseEvent mouseEvent) {
    int row = 8 - (int) (mouseEvent.getY() / squareSize);
    int col = (int) (mouseEvent.getX() / squareSize) - 1;
    Pos pos = new Pos(row, col);
    if (selected == null || board.getGame().legalMoves().containsKey(pos)) {
      if (board.isInsideBounds(pos)
          && !board.isEmpty(pos)
          && board.getGame().legalMoves().containsKey(pos)) {
        selected = board.get(pos);
      }
    } else if (pos.equals(selected.getPos())) {
      selected = null;
    } else if (board.getGame().legalMoves().get(selected.getPos()).containsKey(pos)) {
      board.getGame().makeMove(selected.getPos(), pos);
      selected = null;
    }
    draw();
  }
}
