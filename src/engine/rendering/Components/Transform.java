package engine.rendering.Components;

import engine.shapes.Vector3;

public class Transform {
  public Vector3 position;
  public Vector3 rotation;

  public Transform(Vector3 position, Vector3 rotation) {
    this.position = position;
    this.rotation = rotation;
  }

  public void translate(Vector3 distance) {
    position.translate(distance);
  }

  public void setPosition(Vector3 position) {
    this.position = position;
  }
  
  public void rotate(Vector3 amount) {
    rotation.translate(amount);
  }

  public void setRotation(Vector3 rotation) {
    this.rotation = rotation;
  }
}
