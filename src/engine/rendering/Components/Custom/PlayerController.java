package engine.rendering.Components.Custom;

import engine.Debug;
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
    // Vector2 mouseSpeed = InputManager.mouseSpeed();
    Vector2 mouseSpeed = new Vector2(0, 0);

    if(InputManager.held(Keybind.FORWARD)) {
      velZ = speed/100;
    }
    if(InputManager.held(Keybind.BACK)) {
      velZ = -speed/100;
    }
    if(InputManager.held(Keybind.LEFT)) {
      velX = -speed/100;
    }
    if(InputManager.held(Keybind.RIGHT)) {
      velX = speed/100;
    }
    if(InputManager.held(Keybind.JUMP) && !jumping) {
      rb.velocity.y = -jumpHeight/100;
      jumping = true;
    }

    double xAngle = -mouseSpeed.x * sensitivity;
    double yAngle = -mouseSpeed.y * sensitivity;
    // Scene.mainCamera.rotateCameraY(yAngle);
    // if(Scene.mainCamera.pitch + xAngle <= 70 && Scene.mainCamera.pitch + xAngle >= -70) {
    //   Scene.mainCamera.pitchCamera(xAngle);
    // }

    double angle = (Scene.mainCamera.rotation[1])*3.14159/180;
    double relativeVX = Math.cos(angle) * velX + Math.sin(-angle) * velZ;
    double relativeVZ = Math.sin(angle) * velX + Math.cos(-angle) * velZ;

    rb.velocity.x = relativeVX;
    rb.velocity.z = relativeVZ;
    rb.velocity.y += gravity/1000;

    if(rb.colliding) {
      jumping = false;
      gameObject.transform.position.y -= 0.0001;
      rb.velocity.y = 0;
    }
  }
}
