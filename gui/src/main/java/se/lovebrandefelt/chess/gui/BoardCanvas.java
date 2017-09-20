package se.lovebrandefelt.chess.gui;

import static javafx.scene.paint.Color.ANTIQUEWHITE;
import static javafx.scene.paint.Color.LIGHTSEAGREEN;
import static se.lovebrandefelt.chess.gui.GUI.IMAGES;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.lovebrandefelt.chess.Board;
import se.lovebrandefelt.chess.Piece;
import se.lovebrandefelt.chess.Pos;

public class BoardCanvas extends Canvas {
  private static final Color WHITE_SQUARE_COLOR = ANTIQUEWHITE;
  private static final Color BLACK_SQUARE_COLOR = LIGHTSEAGREEN;
  private Board board;
  private double squareSize;

  public void setBoard(Board board) {
    this.board = board;
    squareSize = getWidth() / board.cols();
    draw();
  }

  public void draw() {
    if (board != null) {
      GraphicsContext graphicsContext = getGraphicsContext2D();
      for (int row = 0; row < board.rows(); row++) {
        for (int col = 0; col < board.cols(); col++) {
          if ((row + col) % 2 == 0) {
            graphicsContext.setFill(WHITE_SQUARE_COLOR);
          } else {
            graphicsContext.setFill(BLACK_SQUARE_COLOR);
          }
          graphicsContext.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
          Pos pos = new Pos(row, col);
          if (!board.isEmpty(pos)) {
            Piece piece = board.get(pos);
            graphicsContext.drawImage(
                IMAGES.get(piece.getColor()).get(piece.getTypeId()),
                col * squareSize,
                row * squareSize,
                squareSize,
                squareSize);
          }
        }
      }
    }
  }
}
