package se.lovebrandefelt.chess;

import se.lovebrandefelt.chess.pieces.Piece;

public class Board {
  private Piece[][] squares;

  public Board(int rows, int columns) {
    this.squares = new Piece[rows][columns];
  }
}
