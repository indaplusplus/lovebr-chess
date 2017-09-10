package se.lovebrandefelt.chess;

public class SetupBuilder {
  private Board setup;

  public SetupBuilder(int rows, int columns) {
    setup = new Board(rows, columns);
  }

  public SetupBuilder add(Piece piece, Pos pos) {
    setup.add(piece, pos);
    return this;
  }

  public SetupBuilder addPawnRow(Color color, int row) {
    for (int i = 0; i < setup.columns(); i++) {
      setup.add(new Pawn(color), new Pos(row, i));
    }
    return this;
  }

  public Board create() {
    return setup;
  }
}
