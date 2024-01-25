package engine.rendering.Components.Custom;

import engine.rendering.Time;
import engine.rendering.Components.Component;
import engine.shapes.Vector3;

public class SuperText extends Component {
  public void start() {
    gameObject.transform.setScale(new Vector3(2, 2, 2));
    gameObject.transform.translate(new Vector3(-6.5, 12, 6.5));
  }
  
  public void update() {
    gameObject.transform.setRotation(new Vector3(90+Time.elapsedTime*100f, 90+Time.elapsedTime*100f, 180+Time.elapsedTime*100f));
  }
}
