package engine.rendering.Components.Custom;

import java.awt.Color;
import java.awt.Font;

import engine.rendering.Scene;
import engine.rendering.Components.Component;
import engine.rendering.UI.TextObject;
import engine.shapes.Vector2;

public class FpsText extends Component {
  TextObject fps;

  int frame = 0;

  public void start() {
    fps = new TextObject(
      "60", new Vector2(0.6, 0.95),
      new Color(0, 255, 0),
      new Font(Font.MONOSPACED, Font.BOLD, 15)
    );

    Scene.UI.addText(fps);
  }

  public void update() {
    frame++;
    if(frame > 5) {
      fps.setText((int)(Scene.fps) + "/" + (int)(Scene.tps));
      frame = 0;
    }
  }
}
