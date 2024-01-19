package engine.rendering.Components.Custom;

import java.util.ArrayList;

import engine.Debug;
import engine.input.InputManager;
import engine.input.Keybind;
import engine.rendering.Scene;
import engine.rendering.Components.*;
import engine.shapes.Vector2;
import engine.shapes.Vector3;

public class PlayerController extends Component {
  double speed = 10;
  double gravity = 4, wallGravity = 1;
  double jumpHeight = 10; 
  double sensitivity = 0.35;

  double velX = 0, velY = 0, velZ = 0;

  boolean canJump = false, grounded = false, onWall = false;

  Collider groundCollider, roofCollider, wallCollider, jumpCollider;

  public void start() {
    groundCollider = new BoxCollider(new Vector3(0.5, 0.25, 0.5), new Vector3(0, 0.375, 0), 0);
    roofCollider = new BoxCollider(new Vector3(0.5, 0.25, 0.5), new Vector3(0, -0.375, 0), 0);
    wallCollider = new BoxCollider(new Vector3(0.5, 0.95, 0.5), new Vector3(0, 0, 0), 1);
    jumpCollider = new BoxCollider(new Vector3(1, 2, 1), new Vector3(0, -0.375, 0), 2);
    
    gameObject.transform.setScale(new Vector3(0.5, 1, 0.5));

    gameObject.addComponent(groundCollider);
    gameObject.addComponent(wallCollider);
    gameObject.addComponent(jumpCollider);
    gameObject.addComponent(roofCollider);
  }
  
  public void update()  {
    grounded = groundCollider.colliding();
    if(grounded) {
      velY = 0;
      while(groundCollider.colliding()) {
        gameObject.transform.position.y -= 0.001;
      }
    }
    if(roofCollider.colliding()) {
      velY = 0;
      while(roofCollider.colliding()) {
        gameObject.transform.position.y += 0.001;
      }
    }

    velX = 0; velZ = 0;
    canJump = jumpCollider.colliding();

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
    if(InputManager.pressed(Keybind.JUMP) && canJump) {
      velY = jumpHeight/100;
    }

    double xAngle = -mouseSpeed.y * sensitivity;
    double yAngle = -mouseSpeed.x * sensitivity;
    Scene.mainCamera.rotateCameraY(yAngle);
    if(Scene.mainCamera.pitch + xAngle <= 70 && Scene.mainCamera.pitch + xAngle >= -70) {
      Scene.mainCamera.pitchCamera(xAngle);
    }

    double angle = (Scene.mainCamera.rotation[1])*3.14159/180;
    double relativeVX = Math.cos(angle) * velX + Math.sin(-angle) * velZ;
    double relativeVZ = Math.sin(angle) * velX + Math.cos(-angle) * velZ;

    velX = relativeVX;
    velZ = relativeVZ;

    gameObject.transform.position.x += velX;
    gameObject.transform.position.z += velZ;
    gameObject.transform.position.y -= velY;

    onWall = wallCollider.colliding();
    if(onWall) {
      gameObject.transform.position.x -= velX;
      gameObject.transform.position.z -= velZ;
      if(!canJump && velY < 0) {
        velY = 0;
      }
      velY -= wallGravity/2000;
    } else {
      velY -= gravity/1000;
    }
    if(gameObject.transform.position.y >= 10) {
      gameObject.transform.position = new Vector3(0, 0, 0);
    }
  }
}
