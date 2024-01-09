package engine.input;

public enum Keybind {
  FORWARD(87),
  BACK(83),
  LEFT(65),
  RIGHT(68),
  JUMP(32),
  DOWN(16),
  ESCAPE(27),
  ;

  public int default_bind;

  Keybind(int d) {
    this.default_bind = d;
  }
}
