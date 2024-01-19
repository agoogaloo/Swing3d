package engine.rendering.Components.Custom;

import engine.rendering.Components.BoxCollider;
import engine.rendering.Components.Component;
import engine.shapes.Vector3;

public class Platform extends Component {
  Vector3 pos, size;

  public Platform(Vector3 position, Vector3 size) {
    this.pos = position; this.size = size;
  }
  
  public void start() {
    gameObject.transform.translate(pos);
    gameObject.transform.setScale(size);
    gameObject.addComponent(new BoxCollider(size, 0));
  }

  public void update() {
  }
}
