package engine.input;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.*;

import engine.shapes.Vector2;

public class MouseMotionHandler implements MouseMotionListener {
  Vector2 previousPos = new Vector2(0, 0);
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
    setPosition(new Vector2(e.getX(), e.getY()));
    // System.out.println("Mouse moved" + e);
  }
  
  public void mouseDragged(MouseEvent e) {
    setPosition(new Vector2(e.getX(), e.getY()));
    // System.out.println("Mouse dragged" + e);
  }
  
  public void setPosition(Vector2 newPos) {
    // robot.mouseMove(previousPos.x, previousPos.y);
    previousPos = currentPos;
    currentPos = newPos;
  }

  public void update() {
    speed = new Vector2(
      currentPos.x - previousPos.x,
      currentPos.y - previousPos.y
    );
  
    previousPos = currentPos;
  }
}
