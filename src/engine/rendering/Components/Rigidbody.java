package engine.rendering.Components;

import engine.shapes.Vector3;

public class Rigidbody extends Component {
  public Vector3 velocity;

  public Rigidbody() {
    velocity = new Vector3(0, 0, 0);
  }
  
  @Override
  public void update() {
    boolean canMove = true;
    if(canMove) {
      gameObject.transform.translate(velocity);
    }
  }
}
