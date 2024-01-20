package engine.rendering.Components.Custom;

import java.util.ArrayList;

import engine.rendering.GameObject;
import engine.rendering.Scene;
import engine.rendering.Components.*;
import engine.shapes.Mesh;
import engine.shapes.MeshColors;
import engine.shapes.Vector3;

public class Startup extends Component {
  Mesh unitCube = new Mesh(new double[][][] {
    {{ 0.0, 0.0, 0.0 }, { 1.0, 1.0, 0.0 }, { 0.0, 1.0, 0.0 }},
    {{ 0.0, 0.0, 0.0 }, { 1.0, 0.0, 0.0 }, { 1.0, 1.0, 0.0 }},
        
    {{ 1.0, 0.0, 0.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 1.0, 0.0 }},
    {{ 1.0, 0.0, 0.0 }, { 1.0, 0.0, 1.0 }, { 1.0, 1.0, 1.0 }},
        
    {{ 1.0, 0.0, 1.0 }, { 0.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }},
    {{ 1.0, 0.0, 1.0 }, { 0.0, 0.0, 1.0 }, { 0.0, 1.0, 1.0 }},
        
    {{ 0.0, 0.0, 1.0 }, { 0.0, 1.0, 0.0 }, { 0.0, 1.0, 1.0 }},
    {{ 0.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0 }, { 0.0, 1.0, 0.0 }},
        
    {{ 0.0, 1.0, 0.0 }, { 1.0, 1.0, 1.0 }, { 0.0, 1.0, 1.0 }},
    {{ 0.0, 1.0, 0.0 }, { 1.0, 1.0, 0.0 }, { 1.0, 1.0, 1.0 }},
        
    {{ 1.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0 }, { 0.0, 0.0, 1.0 }},
    {{ 1.0, 0.0, 1.0 }, { 1.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0 }},
  }, MeshColors.defaultColor);

  GameObject player;

  public void start() {
    Mesh cube = Mesh.copy(unitCube);
    // shipMesh = loadObjectFromFile("VideoShip.obj");
    // axisMesh = loadObjectFromFile("axis.obj");

    player = new GameObject(cube);
    GameObject ground = new GameObject(cube);

    player.addComponent(new PlayerController());
    player.addComponent(new CameraFollow());
    // Scene.mainCamera.setRotation(new double[] { -30, 0, 0 });
    
    Scene.addGameObject(player);
    
    Platform[] platforms = new Platform[] {
      new Platform(new Vector3(0, 0, 4), new Vector3(10, 1, 10), 0),
      new Platform(new Vector3(0, 1, 13), new Vector3(2, 0.5, 2), 0),

      new Platform(new Vector3(4, 4, 22), new Vector3(0.5, 5, 10), 0),
      new Platform(new Vector3(4.2, 4, 22), new Vector3(0.5, 5.5, 10.5), 1),
      new Platform(new Vector3(-1, 4, 30), new Vector3(0.5, 3, 5), 0),
      new Platform(new Vector3(-1.1, 4, 29.25), new Vector3(0.5, 3.5, 5.5), 1),

      new Platform(new Vector3(0, 8, 38), new Vector3(4, 10, 4), 1),
      new Platform(new Vector3(1.75, 7, 35), new Vector3(0.5, 8, 5), 1),
      new Platform(new Vector3(-2, 6, 38), new Vector3(0.5, 4, 3.9), 0),
      new Platform(new Vector3(0, 8, 42.5), new Vector3(4, 3, 0.5), 0),
      new Platform(new Vector3(2, 8, 38), new Vector3(4, 0.5, 1.5), 0),
      new Platform(new Vector3(4, 11.5, 34), new Vector3(2, 4, 0.5), 0),
      new Platform(new Vector3(0, 13, 38), new Vector3(3.5, 0.5, 3.5), 0),

      new Platform(new Vector3(0, 14, 43), new Vector3(0.5, 0.1, 0.5), 0),
      new Platform(new Vector3(-2, 14.5, 48), new Vector3(0.5, 0.1, 0.5), 0),
      new Platform(new Vector3(2, 15.25, 52), new Vector3(0.5, 0.1, 0.5), 0),
      new Platform(new Vector3(2, 15.5, 52), new Vector3(0.5, 0.1, 0.5), 1),
      new Platform(new Vector3(7.5, 14, 54), new Vector3(0.5, 0.1, 0.5), 0),
      new Platform(new Vector3(10, 15, 54), new Vector3(1, 3, 1), 1),
      new Platform(new Vector3(12, 14, 54), new Vector3(0.5, 0.1, 0.5), 0),

      new Platform(new Vector3(19, 15.25, 54.5), new Vector3(6, 4, 0.5), 1),
      new Platform(new Vector3(19, 15.25, 54.4), new Vector3(5, 0.5, 0.5), 0),
      new Platform(new Vector3(31.25, 16.25, 51), new Vector3(14, 4, 0.5), 1),
      new Platform(new Vector3(27.5, 16.25, 51.1), new Vector3(5, 0.5, 0.5), 0),
      new Platform(new Vector3(35.75, 17.5, 51.1), new Vector3(3, 0.5, 0.5), 0),

      new Platform(new Vector3(42.5, 15.5, 53), new Vector3(0.5, 0.5, 3), 0),
      new Platform(new Vector3(48, 16, 53), new Vector3(3, 0.1, 0.05), 0),
      new Platform(new Vector3(53, 16, 50), new Vector3(3, 0.1, 0.05), 0),
      new Platform(new Vector3(58, 16, 47), new Vector3(3, 0.1, 0.05), 0),
      new Platform(new Vector3(62, 16, 42), new Vector3(0.05, 0.1, 3), 0),
      
      new Platform(new Vector3(64, 16.95, 34), new Vector3(0.5, 4, 10), 1),
      new Platform(new Vector3(60, 16.95, 34), new Vector3(0.5, 4, 10), 1),
      new Platform(new Vector3(62, 18.7, 34), new Vector3(4, 0.5, 10), 1),
      new Platform(new Vector3(62, 18.6, 34), new Vector3(3, 0.5, 9.5), 0),
      new Platform(new Vector3(62, 16, 36), new Vector3(0.8, 4, 0.8), 1),
      new Platform(new Vector3(63.5, 16, 32.75), new Vector3(0.8, 4, 0.8), 1),
      new Platform(new Vector3(60.5, 16, 32.75), new Vector3(0.8, 4, 0.8), 1),
      new Platform(new Vector3(62, 16, 29), new Vector3(0.8, 4, 0.8), 1),
      new Platform(new Vector3(60.5, 16, 29), new Vector3(0.8, 4, 0.8), 1),


    };

    for (Platform platform : platforms) {
      GameObject platformObject = new GameObject(cube);
      platformObject.addComponent(platform);

      Scene.addGameObject(platformObject);
    }
    
  }

  public void update() {
    
  }
}
