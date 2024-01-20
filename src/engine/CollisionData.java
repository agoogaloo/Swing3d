package engine;

import java.util.ArrayList;

import engine.rendering.Components.Collider;

public class CollisionData {
  public static ArrayList<Collider> colliders = new ArrayList<Collider>();

  public static boolean isColliding(Collider collider) {
    return isColliding(collider, -1);
  }
  public static boolean isColliding(Collider collider, int mask) {
    for (Collider other : CollisionData.colliders) {
      try {
        if(!other.id.equals(collider.id)) {
          if(collider.intersectsCollider(other)) { 
            if(mask < 0 || other.layer == mask) {
              return true; 
            }
          }
        }
      } catch(NullPointerException e) {
        System.out.println("Collider not found");
      }
    }
    return false;
  }
}
