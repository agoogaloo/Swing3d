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

    player.addComponent(new Rigidbody());
    player.addComponent(new PlayerController());
    player.addComponent(new CameraFollow());
    Scene.mainCamera.setRotation(new double[] { -30, 0, 0 });
    
    Scene.addGameObject(player);
    
    Platform[] platforms = new Platform[] {
      new Platform(new Vector3(0, 0, 4), new Vector3(10, 1, 10), false),
      new Platform(new Vector3(0, 1, 13), new Vector3(2, 0.5, 2), false),
      new Platform(new Vector3(4, 4, 22), new Vector3(0.5, 5, 10), false),
      new Platform(new Vector3(4.2, 4, 22), new Vector3(0.5, 5.5, 10.5), true),
      new Platform(new Vector3(-1, 3, 30), new Vector3(0.5, 3, 5), false),
      new Platform(new Vector3(-1.1, 3, 29.25), new Vector3(0.5, 3.5, 5.5), true),
      new Platform(new Vector3(0, 16, 38), new Vector3(4, 30, 4), true),
      new Platform(new Vector3(2, 6, 35), new Vector3(0.5, 8, 5), true),
      new Platform(new Vector3(-2, 5, 38), new Vector3(0.5, 4, 4.5), false),
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
