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

  GameObject cubeObject;

  public void start() {
    Mesh cube = Mesh.copy(unitCube);
    // shipMesh = loadObjectFromFile("VideoShip.obj");
    // axisMesh = loadObjectFromFile("axis.obj");

    cubeObject = new GameObject(cube);
    GameObject ground = new GameObject(cube);

    cubeObject.addComponent(new Rigidbody());
    cubeObject.addComponent(new CameraController());

    Rigidbody rb = (Rigidbody)cubeObject.getComponent(Rigidbody.class);
    rb.velocity = new Vector3(0.0, -0.01, 0);
    
    cubeObject.transform.translate(new Vector3(0, -2, 2));
    cubeObject.transform.rotate(new Vector3(0, 45, 45));
    cubeObject.transform.setScale(new Vector3(0.5, 0.5, 0.5));
    
    ground.transform.translate(new Vector3(0, 1, 4));
    ground.transform.setScale(new Vector3(10, 1, 10));
    ground.transform.rotate(new Vector3(0, 0, 0));
    
    Scene.addGameObject(cubeObject);
    Scene.addGameObject(ground);
  }

  public void update() {
    Rigidbody rb = (Rigidbody)cubeObject.getComponent(Rigidbody.class);
    rb.velocity.y+=0.004;
  }
}
