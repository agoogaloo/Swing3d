package engine.input;

public enum Keybind {
  FORWARD(87),
  BACK(83),
  LEFT(65),
  RIGHT(68),
  JUMP(32),
  DOWN(16),
  ESCAPE(27),
  ONE(49),
  TWO(50),
  THREE(51),
  FOUR(52),
  FIVE(53),
  SIX(54),
  SEVEN(55),
  EIGHT(56),
  NINE(57),
  ZERO(48)
  ;

  public int default_bind;

  Keybind(int d) {
    this.default_bind = d;
  }
}
