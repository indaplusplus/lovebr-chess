package se.lovebrandefelt.chess.pieces;

import se.lovebrandefelt.chess.Board;
import se.lovebrandefelt.chess.Position;

import java.util.List;

public abstract class Piece {
  private Board board;
  private Position position;

  Piece(Board board, Position position) {
    this.position = position;
  }

  public abstract List<Position> validMoves();

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }
}
