package engine.rendering.Components;

import engine.rendering.GameObject;
import engine.shapes.Vector3;

public class Transform extends Component {
  public Vector3 position;
  public Vector3 rotation;
  public Vector3 scale;

  public Transform(GameObject gameObject, Vector3 position, Vector3 rotation) {
    this.gameObject = gameObject;
    this.position = position;
    this.rotation = rotation;
    this.scale = new Vector3(1, 1, 1);
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

  public void scale(Vector3 scale) {
    this.scale = new Vector3(this.scale.x * scale.x, this.scale.y * scale.y, this.scale.z * scale.z);
  }

  public void setScale(Vector3 scale) {
    this.scale = scale;
  }
}
