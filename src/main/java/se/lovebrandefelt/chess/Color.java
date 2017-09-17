package se.lovebrandefelt.chess;

public enum Color {
  WHITE,
  BLACK;

  /**
   * Returns the next color.
   * @return the next color
   */
  public Color next() {
    return Color.values()[(this.ordinal() + 1) % Color.values().length];
  }
}
