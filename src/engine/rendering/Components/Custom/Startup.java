package engine.rendering.Components.Custom;

import java.util.ArrayList;

import engine.rendering.GameObject;
import engine.rendering.Scene;
import engine.rendering.Components.*;
import engine.shapes.Mesh;
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
  }, new double[][] {
    { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
    { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
    { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
    { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
    { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
    { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
  });

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
      new Platform(new Vector3(0, 0, 0), new Vector3(10, 1, 10)),
      new Platform(new Vector3(0, 1, 13), new Vector3(2, 0.5, 2)),
      new Platform(new Vector3(4, 4, 22), new Vector3(0.5, 5, 10)),
      new Platform(new Vector3(0, 3, 4), new Vector3(5, 1, 5)),
      // new Platform(new Vector3(0, 0, 0), new Vector3(0, 0, 0)),
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
