package se.lovebrandefelt.chess.pieces;

import se.lovebrandefelt.chess.Board;
import se.lovebrandefelt.chess.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
  public Pawn(Board board, Position position) {
    super(board, position);
  }

  @Override
  public List<Position> validMoves() {
    List<Position> validMoves = new ArrayList<>();
    validMoves.add(getPosition().offset(1, 0));
    return validMoves;
  }
}
