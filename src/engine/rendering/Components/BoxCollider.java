package engine.rendering.Components;

import engine.Debug;
import engine.shapes.Vector3;

public class BoxCollider extends Collider {
  public Vector3 size;

  public BoxCollider(Vector3 size) {
    this.size = size;
  }

  public boolean containsPoint(Vector3 point) {
    return point.between(
      getMin(),
      getMax()
    );
  }

  public Vector3[] getCorners() {
    Vector3 min = getMin();
    Vector3 max = getMax();
    return new Vector3[] {
      new Vector3(min.x, min.y, min.z),
      //
      new Vector3(max.x, min.y, min.z),
      new Vector3(min.x, max.y, min.z),
      new Vector3(min.x, min.y, max.z),
      //
      new Vector3(max.x, max.y, min.z),
      new Vector3(max.x, min.y, max.z),
      new Vector3(min.x, max.y, max.z),
      //
      new Vector3(max.x, max.y, max.z),
    };
  }
  
  public Vector3 getMin() {
    return Vector3.add(gameObject.transform.position, Vector3.scalarMultiply(size, 0.5).negative());
  }

  public Vector3 getMax() {
    return Vector3.add(gameObject.transform.position, Vector3.scalarMultiply(size, 0.5));
  }

  public boolean intersectsCollider(Collider other) {
    if(other instanceof BoxCollider) {
      BoxCollider box = (BoxCollider)other;

      Vector3[] points = box.getCorners();
      Vector3 min = getMin();
      Vector3 max = getMax();
      for (Vector3 point : points) {
        if(point.between(min, max)) { return true; }
      }

      points = this.getCorners();
      min = box.getMin();
      max = box.getMax();
      for (Vector3 point : points) {
        if(point.between(min, max)) { return true; }
      }
    }
    return false;
  }

  public void update() {
    // debug();
  }

  public void debug() {
    Vector3[] points = getCorners();
    for (Vector3 point : points) {
      Debug.drawPoint(new double[] { point.x, point.y, point.z });
    }
  }
}
