package engine.rendering.Components.Custom;

import java.awt.Color;
import java.awt.Font;

import engine.Main;
import engine.rendering.Scene;
import engine.rendering.Time;
import engine.rendering.Components.Component;
import engine.rendering.UI.TextObject;
import engine.shapes.Vector2;

public class TempText extends Component {
  public TextObject text, text2, text3, fps;

  public void start() {
    text = new TextObject(
      "", new Vector2(-0.95, 0.5),
      new Color(0, 0, 255),
      new Font(Font.MONOSPACED, Font.BOLD, 25)
    );
    text2 = new TextObject(
      "", new Vector2(-0.95, 0.2),
      new Color(0, 0, 255),
      new Font(Font.MONOSPACED, Font.BOLD, 25)
    );
    text3 = new TextObject(
      "", new Vector2(-0.95, -0.1),
      new Color(0, 0, 255),
      new Font(Font.MONOSPACED, Font.BOLD, 25)
    );
    fps = new TextObject(
      "60", new Vector2(0.85, 0.95),
      new Color(0, 255, 0),
      new Font(Font.MONOSPACED, Font.BOLD, 15)
    );
    Scene.UI.addText(text);
    Scene.UI.addText(text2);
    Scene.UI.addText(text3);
    Scene.UI.addText(fps);
  }

  public void update() {
    if(Main.lagging) {
      text.setText("GPU ACCELERATION IS");
      text2.setText("AN ACTIVE AREA OF");
      text3.setText("RESEARCH");
    } else {
      text.setText("");
    }

    fps.setText("" + (int)(Main.currentFPS));
  }
}
