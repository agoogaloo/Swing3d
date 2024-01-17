package engine.rendering.Components.Custom;

import engine.rendering.Components.Component;
import engine.rendering.Components.Rigidbody;
import engine.shapes.Vector3;

public class FallingPlatform extends Component {
  Vector3 pos, size;

  public FallingPlatform(Vector3 position, Vector3 size) {
    this.pos = position; this.size = size;
  }
  
  public void start() {
    gameObject.transform.translate(pos);
    gameObject.transform.setScale(size);
  }

  public void update() {

  }
}
