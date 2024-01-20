package engine.input;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.*;

import javax.swing.SwingUtilities;

import engine.Main;
import engine.shapes.Vector2;

public class MouseMotionHandler implements MouseMotionListener {
  Vector2 previousPos = new Vector2(Main.getWindow().getWindowWidth()/2, Main.getWindow().getWindowHeight()/2);
  //Vector2 home;
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
      detected = true;
    }
  }
  
  public void update() {
    previousPos = new Vector2(Main.getWindow().getWindowWidth()/2, Main.getWindow().getWindowHeight()/2);
    Point home = new Point((int)previousPos.x,(int)previousPos.y);
    SwingUtilities.convertPointToScreen(home, Main.getWindow().getFrame());
    if(detected && enable) {
      speed = new Vector2(
        currentPos.x - previousPos.x,
        currentPos.y - previousPos.y
      );
      
      robot.mouseMove(home.x, home.y);
      
    }
    if(InputManager.pressed(Keybind.ESCAPE)) {
      enable = !enable;
      detected = false;
      speed = new Vector2(0, 0);
    }
  }
}
