package engine.rendering.Components;

import engine.CollisionData;
import engine.shapes.Mesh;
import engine.shapes.Vector;
import engine.shapes.Vector3;

public class Rigidbody extends Component {
  public Vector3 velocity;
  public boolean colliding = false;

  public Rigidbody() {
    velocity = new Vector3(0, 0, 0);
  }
  
  @Override
  public void update() {
    colliding = !canMove();
    if(!colliding) {
      gameObject.transform.translate(velocity);
    } else {
      velocity = new Vector3(0, 0, 0);
    }
  }

  boolean canMove() {
    for(int t = 0; t < gameObject.mesh.triangles.length; t++) {
      for (int i = 0; i < 3; i++) {
        double[] point1 = gameObject.getWorldMesh().triangles[t][i];
        double[] point2 = Vector.add(point1, velocity.toDouble());
        for (Mesh mesh : CollisionData.meshes) {
          if(!mesh.id.equals(gameObject.mesh.id)) {
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
