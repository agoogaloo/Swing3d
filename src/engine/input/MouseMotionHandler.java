package engine.input;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.*;

import engine.shapes.Vector2;

public class MouseMotionHandler implements MouseMotionListener {
  Vector2 previousPos = new Vector2(300, 300);
  Vector2 home;
  boolean detected = false;
  boolean enable = true;
  Robot robot;

  public Vector2 currentPos = new Vector2(0, 0);
  public Vector2 speed = new Vector2(0, 0);

  
  public MouseMotionHandler() {
    try {
      robot = new Robot();
    } catch(AWTException e) {
      System.out.println("Unable to control mouse");
    }

  }

  public void mouseMoved(MouseEvent e) {
    setPosition(e);
  }
  
  public void mouseDragged(MouseEvent e) {
    setPosition(e);
  }
  
  public void setPosition(MouseEvent e) {
    Vector2 newPos = new Vector2(e.getX(), e.getY());
    currentPos = newPos;
    if(!detected) {
      double distX = 300 - newPos.x;
      double distY = 300 - newPos.y;
      
      home = new Vector2(e.getXOnScreen() + distX, e.getYOnScreen() + distY);
      detected = true;
    }
  }
  
  public void update() {
    if(detected && enable) {
      speed = new Vector2(
        currentPos.x - previousPos.x,
        currentPos.y - previousPos.y
      );
      robot.mouseMove((int)home.x, (int)home.y);
    }
    if(InputManager.pressed(Keybind.ESCAPE)) {
      enable = !enable;
      detected = false;
      speed = new Vector2(0, 0);
    }
  }
}
