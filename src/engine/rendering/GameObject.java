package engine.rendering;

import engine.rendering.Components.Transform;
import engine.shapes.Mesh;
import engine.shapes.Vector3;

public class GameObject {
  public Transform transform;
  public Mesh mesh;
  public Vector3 velocity;

  public GameObject(Mesh mesh) {
    this(mesh, new Vector3(0, 0, 0), new Vector3(0, 0, 0));
  }

  public GameObject(Mesh mesh, Vector3 position) {
    this(mesh, position, new Vector3(0, 0, 0));
  }
  
  public GameObject(Mesh mesh, Vector3 position, Vector3 rotation) {
    this.transform = new Transform(position, rotation);
    this.mesh = mesh;
    this.velocity = new Vector3(0, 0, 0);
  }

  public void update() {
    this.transform.translate(velocity);
  }

  public Mesh getWorldMesh() {
    Mesh outputMesh = Mesh.copy(mesh);
    outputMesh.translate(Vector3.halfUnit.negative());
    outputMesh.scale(transform.scale);
    outputMesh.rotate(transform.rotation);
    outputMesh.translate(Vector3.halfUnit);
    outputMesh.translate(transform.position);

    return outputMesh;
  }
}
