package engine.rendering.Components;

import engine.shapes.Vector3;

public abstract class Collider extends Component {
  public String id;

  public abstract boolean containsPoint(Vector3 point);
  public abstract boolean intersectsCollider(Collider other);
  public abstract void debug();
}
