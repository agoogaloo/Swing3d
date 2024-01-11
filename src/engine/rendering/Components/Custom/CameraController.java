package engine.rendering.Components.Custom;

import engine.input.InputManager;
import engine.input.Keybind;
import engine.rendering.Scene;
import engine.rendering.Components.Component;
import engine.shapes.Vector2;

public class CameraController extends Component {
  double speed = 0.1;
  double sensitivity = 0.75;

  public void update()  {
    double velX = 0, velY = 0;
    Vector2 mouseSpeed = InputManager.mouseSpeed();
    if(InputManager.held(Keybind.FORWARD)) {
      velX = speed;
    }
    if(InputManager.held(Keybind.BACK)) {
      velX = -speed;
    }
    if(InputManager.held(Keybind.LEFT)) {
      velY = -speed;
    }
    if(InputManager.held(Keybind.RIGHT)) {
      velY = speed;
    }
    if(InputManager.held(Keybind.JUMP)) {
      Scene.mainCamera.cameraPosition[1] -= speed;
    }
    if(InputManager.held(Keybind.DOWN)) {
      Scene.mainCamera.cameraPosition[1] += speed;
    }
    if(!mouseSpeed.zero()) {
      // Scene.mainCamera.rotateCamera(new double[] { -mouseSpeed.x * sensitivity, -mouseSpeed.y * sensitivity, 0 });
    }
    if(velX != 0 || velY != 0) {
      // Scene.mainCamera.cameraMove(velX, velY);
    }
  }
}
