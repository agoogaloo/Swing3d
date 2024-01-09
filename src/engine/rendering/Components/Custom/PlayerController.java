package engine.rendering.Components.Custom;

import engine.input.InputManager;
import engine.input.Keybind;
import engine.rendering.Scene;
import engine.rendering.Components.*;
import engine.shapes.Vector2;
import engine.shapes.Vector3;

public class PlayerController extends Component {
  double speed = 10;
  double gravity = 4;
  double jumpHeight = 10; 
  double sensitivity = 0.75;

  boolean jumping = false;

  Rigidbody rb;

  public void start() {
    rb = (Rigidbody)gameObject.getComponent(Rigidbody.class);
  }
  
  public void update()  {
    double velX = 0, velZ = 0;
    Vector2 mouseSpeed = InputManager.mouseSpeed();
    if(InputManager.held(Keybind.FORWARD)) {
      velX = speed/100;
      // Scene.mainCamera.rotateCamera(new double[] { 1, 0, 0 });
    }
    if(InputManager.held(Keybind.BACK)) {
      velX = -speed/100;
      // Scene.mainCamera.rotateCamera(new double[] { -1, 0, 0 });
    }
    if(InputManager.held(Keybind.LEFT)) {
      velZ = -speed/100;
      // Scene.mainCamera.rotateCamera(new double[] { 0, 1, 0 });
    }
    if(InputManager.held(Keybind.RIGHT)) {
      velZ = speed/100;
      // Scene.mainCamera.rotateCamera(new double[] { 0, -1, 0 });
    }
    if(InputManager.held(Keybind.JUMP) && !jumping) {
      rb.velocity.y = -jumpHeight/100;
      jumping = true;
    }

    // if(!mouseSpeed.zero()) {

    rb.velocity.x = velZ;
    rb.velocity.z = velX;
    rb.velocity.y += gravity/1000;

    if(rb.colliding) {
      jumping = false;
      gameObject.transform.position.y -= 0.0001;
      rb.velocity.y = 0;
    }
  }
}
