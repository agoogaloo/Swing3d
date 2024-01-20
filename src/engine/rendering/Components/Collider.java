package engine.rendering.Components;

import engine.CollisionData;
import engine.rendering.Scene;
import engine.shapes.Vector3;

public abstract class Collider extends Component {
  public String id;
  public int layer;

  public Collider(int layer) {
    this.layer = layer;
  }

  public void start() {
    this.id = gameObject.id;
    Scene.addCollider(this);
  }

  public abstract boolean containsPoint(Vector3 point);
  public abstract boolean intersectsCollider(Collider other);
  public abstract void debug();

  public boolean colliding() { 
    return CollisionData.isColliding(this);
  }
  public boolean colliding(int mask) {
    return CollisionData.isColliding(this, mask);
  }
}
