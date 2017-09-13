package se.lovebrandefelt.chess;

public enum Color {
  WHITE,
  BLACK;

  Color next() {
    return Color.values()[(this.ordinal() + 1) % Color.values().length];
  }
}
