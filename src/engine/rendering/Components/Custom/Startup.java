package engine.rendering.Components.Custom;

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

    player.setCollider(new BoxCollider(new Vector3(0.5, 1, 0.5)));
    player.addComponent(new Rigidbody());
    player.addComponent(new PlayerController());
    player.addComponent(new CameraFollow());
    Scene.mainCamera.setRotation(new double[] { -30, 0, 0 });

    Rigidbody rb = (Rigidbody)player.getComponent(Rigidbody.class);
    // rb.velocity = new Vector3(0.0, -0.01, 0);
    
    player.transform.translate(new Vector3(0, -2, 2));
    // player.transform.rotate(new Vector3(0, 45, 45));
    player.transform.setScale(new Vector3(0.5, 1, 0.5));
    
    ground.setCollider(new BoxCollider(new Vector3(10, 1, 10)));
    ground.transform.translate(new Vector3(0, 1, 4));
    ground.transform.setScale(new Vector3(10, 1, 10));
    ground.transform.rotate(new Vector3(0, 0, 0));
    
    Scene.addGameObject(player);
    Scene.addGameObject(ground);
    
    GameObject platform = new GameObject(cube);
    platform.addComponent(new Rigidbody());
    platform.addComponent(new FallingPlatform(new Vector3(0, 0, 10), new Vector3(1, 0.5, 1)));
    Scene.addGameObject(platform);
  }

  public void update() {
    
  }
}
