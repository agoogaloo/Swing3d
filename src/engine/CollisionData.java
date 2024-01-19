package engine;

import java.util.ArrayList;

import engine.rendering.Components.Collider;

public class CollisionData {
  public static ArrayList<Collider> colliders = new ArrayList<Collider>();

  public static boolean isColliding(Collider collider) {
    for (Collider other : CollisionData.colliders) {
      try {
        if(!other.id.equals(collider.id)) {
          if(collider.intersectsCollider(other)) { return true; }
        }
      } catch(NullPointerException e) {
        System.out.println("Collider not found");
      }
    }
    return false;
  }
}
