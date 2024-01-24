package engine.input;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import javax.swing.SwingUtilities;

import engine.Main;
import engine.shapes.Vector2;

public class MouseMotionHandler {
  Vector2 previousPos = new Vector2(0.5, 0.5);
  Point home;
  Robot robot;

  boolean enable = true;

  public Vector2 currentPos = new Vector2(0, 0);
  public Vector2 speed = new Vector2(0, 0);

  public MouseMotionHandler() {
    try {
      robot = new Robot();
    } catch(AWTException e) {
      System.out.println("Unable to control mouse");
    }
  }
  
  public void update() {
    if(InputManager.pressed(Keybind.ESCAPE)) {
      enable = !enable;
    }
    
    if(enable) {
      Point pos = MouseInfo.getPointerInfo().getLocation();
      SwingUtilities.convertPointFromScreen(pos, Main.getWindow().getFrame());
      currentPos = new Vector2(
        pos.getX()/(double)Main.getWindow().getWindowWidth(),
        pos.getY()/(double)Main.getWindow().getWindowHeight()
      );

      speed.x = ((currentPos.x - previousPos.x)+speed.x)/2.0;
      speed.y = ((currentPos.y - previousPos.y)+speed.y)/2.0;
      if(Math.abs(speed.x) < 0.001) {
        speed.x = 0;
      }
      if(Math.abs(speed.y) < 0.001) {
        speed.y = 0;
      }
      
      robit();
    } else {
      speed = new Vector2(0, 0);
    }
  }
    
  void robit() {
    Point home = new Point(
      (int)((double)Main.getWindow().getWindowWidth()/2.0),
      (int)((double)Main.getWindow().getWindowHeight()/2.0)
    );
    SwingUtilities.convertPointToScreen(home, Main.getWindow().getFrame());
    
    robot.mouseMove((int)home.x, (int)home.y);
  }
  
  public Vector2 speed() {
    Vector2 speedCopy = new Vector2(speed);
    return speedCopy;
  }

  public Vector2 position() {
    return currentPos;
  }
}
