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
    gameObject.transform.translate(velocity);
    colliding = !canMove();
    if(colliding) {
      gameObject.transform.translate(velocity.negative());
      velocity = new Vector3(0, 0, 0);
    }
  }

  boolean canMove() {
  //   if(gameObject.collider == null) { return true; }
  //   for (Collider collider : CollisionData.colliders) {
  //     try {
  //       if(!collider.id.equals(gameObject.collider.id)) {
  //         if(collider.intersectsCollider(gameObject.collider)) { return false; }
  //       }
  //     } catch(NullPointerException e) {
  //       System.out.println("Collider not found");
  //     }
  //   }
    return true;
  }
}
