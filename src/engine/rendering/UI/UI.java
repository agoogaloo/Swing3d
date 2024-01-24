package engine.rendering.UI;

import java.util.ArrayList;

public class UI {
  public ArrayList<TextObject> text = new ArrayList<TextObject>();

  public void addText(TextObject text) {
    this.text.add(text);
  }

  public void clear() {
    text = new ArrayList<TextObject>();
  }
}
