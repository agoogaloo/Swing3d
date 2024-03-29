package engine.rendering.Components.Custom;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import engine.Debug;
import engine.input.InputManager;
import engine.input.Keybind;
import engine.rendering.Scene;
import engine.rendering.Time;
import engine.rendering.Components.*;
import engine.rendering.UI.TextObject;
import engine.shapes.Vector2;
import engine.shapes.Vector3;

public class PlayerController extends Component {
  double speed = 10;
  double gravity = 4;
  double jumpHeight = 10; 

  double sensitivity = 70;
  double maxAngle = 85;

  double velX = 0, velY = 0, velZ = 0, bump = 0.0001;
  Vector2 cameraTarget = new Vector2(0, 0);

  boolean canJump = false, grounded = false, onWall = false;
  int jumpPrebuffer = 0;

  Collider groundCollider, roofCollider, wallCollider, jumpCollider;

  int frame = 0;
  double startTime = 0, bestTime = 0;
  TextObject timer = new TextObject();
  TextObject bestTimer = new TextObject();
  TextObject pauseText, text1, text2, text3, text4, text5;

  boolean menu = true, paused = false;

  public void start() {
    groundCollider = new BoxCollider(new Vector3(0.5, 0.25, 0.5), new Vector3(0, 0.375, 0), 0);
    roofCollider = new BoxCollider(new Vector3(0.5, 0.25, 0.5), new Vector3(0, -0.375, 0), 0);
    wallCollider = new BoxCollider(new Vector3(0.5, 0.95, 0.5), new Vector3(0, 0, 0), 0);
    jumpCollider = new BoxCollider(new Vector3(1, 2, 1), new Vector3(0, -0.375, 0), 0);
    
    gameObject.transform.setScale(new Vector3(0.1, 0.1, 0.1));

    gameObject.addComponent(groundCollider);
    gameObject.addComponent(wallCollider);
    gameObject.addComponent(jumpCollider);
    gameObject.addComponent(roofCollider);

    timer.setText("");
    timer.setPosition(new Vector2(-0.97, 1.03));
    timer.setColor(new Color(255, 0, 0));
    bestTimer.setText("");
    bestTimer.setPosition(new Vector2(-0.97, 0.86));
    bestTimer.setColor(new Color(255, 0, 0));
    bestTimer.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 10));

    pauseText = new TextObject(
      "", new Vector2(-0.35, 0.75),
      new Color(255, 0, 0),
      new Font(Font.DIALOG, Font.BOLD, 30)
    );

    text1 = new TextObject(
    "", new Vector2(-0.15, 0.75),
      new Color(255, 255, 255),
      new Font(Font.DIALOG, Font.BOLD, 30)
    );
    text2 = new TextObject(
      "", new Vector2(-0.75, 0.25),
      new Color(255, 255, 255),
      new Font(Font.DIALOG, Font.BOLD, 20)
    );
    text3 = new TextObject(
      "", new Vector2(-0.5, -0.1),
      new Color(255, 255, 255),
      new Font(Font.DIALOG, Font.BOLD, 15)
    );
    text4 = new TextObject(
      "", new Vector2(-0.5, -0.4),
      new Color(255, 255, 255),
      new Font(Font.DIALOG, Font.BOLD, 10)
    );
    text5 = new TextObject(
      "", new Vector2(-0.65, -0.7),
      new Color(255, 255, 255),
      new Font(Font.DIALOG, Font.BOLD, 9)
    );
        
    Scene.UI.addText(timer);
    Scene.UI.addText(bestTimer);
    Scene.UI.addText(pauseText);
    Scene.UI.addText(text1);
    Scene.UI.addText(text2);
    Scene.UI.addText(text3);
    Scene.UI.addText(text4);
    Scene.UI.addText(text5);

    respawn();
  }
  
  public void update()  {
    if(InputManager.pressed(Keybind.ESCAPE)) {
      paused = !paused;
      if(!menu) {
        if(paused) {
          pauseText.setText("Paused");
        } else {
          pauseText.setText("");
        }
      }
    }
    if(menu) {
      menu();
    }
    if(paused || menu) {
      return;
    }
    frame++;
    textUpdate();

    grounded = groundCollider.colliding(0);
    velX = 0; velZ = 0;
    canJump = jumpCollider.colliding();

    verticalCollision();
    updateCamera();
    updateMovement();
    movePlayer();
    triggerCollisions();
  }

  void verticalCollision() {
    if(grounded) {
      velY = 0;
      while(groundCollider.colliding(0)) {
        gameObject.transform.position.y -= bump;
      }
    }
    if(roofCollider.colliding(0)) {
      velY = 0;
      while(roofCollider.colliding(0)) {
        gameObject.transform.position.y += bump;
      }
    }
  }

  void textUpdate() {
    float[] HSV = new float[3];
    Color.RGBtoHSB(0, 255, 0, HSV);
    float offset = (float)Time.elapsedTime/20f;
    Color newColor = new Color(Color.HSBtoRGB(
      (float)(HSV[0] + offset), 
      HSV[1], HSV[2]
    ));
    timer.setColor(newColor);
    bestTimer.setColor(newColor);
    timer.setText(String.format("%.4f", frame/60.0));
  }

  void updateCamera() {
    Vector2 mouseSpeed = InputManager.mouseSpeed();
    // Vector2 mouseSpeed = new Vector2(0.02, 0);

    cameraTarget.x -= mouseSpeed.x * sensitivity;
    cameraTarget.y -= mouseSpeed.y * sensitivity;

    if(cameraTarget.y >= maxAngle) {
      cameraTarget.y = maxAngle;
    } else if(cameraTarget.y <= -maxAngle) {
      cameraTarget.y = -maxAngle;
    }

    Vector2 cameraSpeed = new Vector2(
      (cameraTarget.x - Scene.mainCamera.yaw),
      (cameraTarget.y - Scene.mainCamera.pitch)
    );

    Scene.mainCamera.rotateCameraY(cameraSpeed.x);
    Scene.mainCamera.pitchCamera(cameraSpeed.y);
  }

  void updateMovement() {
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
    if(InputManager.pressed(Keybind.JUMP)) {
      jumpPrebuffer = 0;
      if(canJump) { velY = jumpHeight/100; }
    } else if(InputManager.held(Keybind.JUMP) && canJump && (jumpPrebuffer <= 5)) {
      velY = jumpHeight/100;
    }
    
    jumpPrebuffer++;
  }

  void movePlayer() {
    double angle = (Scene.mainCamera.yaw)*Math.PI/180;
    double relativeVX = Math.cos(angle) * velX + Math.sin(-angle) * velZ;
    double relativeVZ = Math.sin(angle) * velX + Math.cos(-angle) * velZ;

    velX = relativeVX;
    velZ = relativeVZ;
    
    onWall = false;

    gameObject.transform.position.x += velX;
    if(wallCollider.colliding(0)) {
      onWall = true;
      gameObject.transform.position.x -= velX;
    }
    
    gameObject.transform.position.z += velZ;
    if(wallCollider.colliding(0)) {
      onWall = true;
      gameObject.transform.position.z -= velZ;
    }

    gameObject.transform.position.y -= velY;
    
    if(onWall) {
      if(!canJump && velY < 0) {
        velY = 0;
      }
    }

    velY -= gravity/1000;
  }

  void triggerCollisions() {
    if(gameObject.transform.position.y >= 10) {
      respawn();
    }
    
    if(jumpCollider.colliding(2)) {
      double delta = Time.elapsedTime - startTime;
      startTime = Time.elapsedTime;
      System.out.println("ggwp" + delta);
      if(bestTime == 0 || delta <= bestTime) {
        bestTime = delta;
      }
      bestTimer.setText(String.format("Best:%.4f", bestTime));
      respawn();
    }

    if(wallCollider.colliding(1)) {
      respawn();
    }
  }
  
  void respawn() {
    gameObject.transform.position = new Vector3(0, -1, 0);
    velX = 0; velY = 0; velZ = 0;
    frame = 0;
  }
    
    void menu() {
    if(frame == 0) {
      frame++;
      text1.setText("Jumping");
      text2.setText("on Abstract Shapes");
      text3.setText("in the Void to Reach");
      text4.setText("an Unknown Position 64");
      text5.setText("press space to start pressing space");
    }
    gameObject.transform.position = new Vector3(0, -10, 0);

    if(InputManager.pressed(Keybind.JUMP)) {
      menu = false;
      text1.setText("");
      text2.setText("");
      text3.setText("");
      text4.setText("");
      text5.setText("");
      startTime = Time.elapsedTime;
    }
  }

  double clamp(double min, double max, double t) {
    if(t < min) { return min; }
    if(t > max) { return max; }
    return t;
  }
}
