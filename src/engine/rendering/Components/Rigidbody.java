package engine.rendering.Components;

import engine.CollisionData;
import engine.shapes.Mesh;
import engine.shapes.Vector;
import engine.shapes.Vector3;

public class Rigidbody extends Component {
  public Vector3 velocity;

  public Rigidbody() {
    velocity = new Vector3(0, 0, 0);
  }
  
  @Override
  public void update() {
    if(canMove()) {
      gameObject.transform.translate(velocity);
    }
  }

  boolean canMove() {
    for(int t = 0; t < gameObject.mesh.triangles.length; t++) {
      for (int i = 0; i < 3; i++) {
        double[] point1 = gameObject.getWorldMesh().triangles[t][i];
        double[] point2 = Vector.add(point1, velocity.toDouble());
        for (Mesh mesh : CollisionData.meshes) {
          if(mesh != CollisionData.meshes[0]) {
            for (double[][] triangle : mesh.triangles) {
              if(Vector.lineIntersectsTriangle(triangle[0], triangle[1], triangle[2], point1, point2)) {
                return false;
              }
            }
          }
        }
      }
    }
    return true;
  }
}
