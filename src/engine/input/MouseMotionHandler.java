package engine.input;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.*;

import javax.swing.SwingUtilities;

import engine.Main;
import engine.shapes.Vector2;

public class MouseMotionHandler implements MouseMotionListener {
  Vector2 previousPos = new Vector2(0.5, 0.5);
  Point home;
  Robot robot;

  boolean enable = true;
  boolean robit = false;

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
    currentPos = new Vector2(
      (double)e.getX()/(double)Main.getWindow().getWindowWidth(),
      (double)e.getY()/(double)Main.getWindow().getWindowHeight()
    );

    if(enable) {
      if(robit) {
        robit = false;
      } else {
        speed.x += (currentPos.x - previousPos.x);
        speed.y += (currentPos.y - previousPos.y);

        robit();
      }
    }
  }
  
  public void update() {
    if(InputManager.pressed(Keybind.ESCAPE)) {
      enable = !enable;
    }
    
    if(!enable) {
      speed = new Vector2(0, 0);
    }
  }
    
  void robit() {
    Point home = new Point(
      Main.getWindow().getWindowWidth()/2, 
      Main.getWindow().getWindowHeight()/2
    );
    SwingUtilities.convertPointToScreen(home, Main.getWindow().getFrame());
    
    robot.mouseMove((int)home.x, (int)home.y);
    robit = true;
  }
  
  public Vector2 speed() {
    Vector2 speedCopy = new Vector2(speed);
    speed.x *= 0.65;
    speed.y *= 0.65;
    return speedCopy;
  }

  public Vector2 position() {
    return currentPos;
  }
}
