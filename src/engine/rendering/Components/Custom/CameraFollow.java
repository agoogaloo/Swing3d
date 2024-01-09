package engine.rendering.Components.Custom;

import engine.input.InputManager;
import engine.input.Keybind;
import engine.rendering.Scene;
import engine.rendering.Components.Component;
import engine.shapes.Vector2;

public class CameraFollow extends Component {
  double xOffset = 0.5;
  double yOffset = 1;
  double zOffset = 0.5;

  public void update()  {
    Scene.mainCamera.cameraPosition[0] = gameObject.transform.position.x + xOffset;
    Scene.mainCamera.cameraPosition[1] = gameObject.transform.position.y - yOffset;
    Scene.mainCamera.cameraPosition[2] = gameObject.transform.position.z + zOffset;
  }
}
