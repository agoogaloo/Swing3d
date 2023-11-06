package engine.rendering;

import engine.rendering.Components.Transform;
import engine.shapes.Mesh;
import engine.shapes.Vector3;

public class GameObject {
  public Transform transform;
  public Mesh mesh;

  public GameObject(Mesh mesh) {
    this(mesh, Vector3.origin, Vector3.origin);
  }

  public GameObject(Mesh mesh, Vector3 position) {
    this(mesh, position, Vector3.origin);
  }

  public GameObject(Mesh mesh, Vector3 position, Vector3 rotation) {
    this.transform = new Transform(position, rotation);
    this.mesh = mesh;
  }
}
