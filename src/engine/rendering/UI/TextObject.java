package engine.rendering.UI;

import java.awt.Color;
import java.awt.Font;

import engine.shapes.Vector2;

public class TextObject {
  public String text;
  public Vector2 position;
  public Color color;
  public Font font = new Font(Font.MONOSPACED, Font.BOLD, 15);

  public TextObject(String text, Vector2 position) {
    this.text = text;
    this.position = position;
  }

  public TextObject() {
    this("", new Vector2(0, 0));
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void setFont(Font font) {
    this.font = font;
  }
  public void setFontSize(int size) {
    this.font = new Font(font.getName(), font.getStyle(), size);
  }
}
