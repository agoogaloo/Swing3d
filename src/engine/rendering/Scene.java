package engine.rendering;

import java.util.ArrayList;

import engine.CollisionData;
import engine.audio.MusicManager;
import engine.rendering.Components.Collider;
import engine.rendering.Components.Component;
import engine.rendering.Shaders.FragmentShader;
import engine.rendering.UI.UI;
import engine.shapes.Mesh;
import engine.stateMachine.GameState;

public class Scene {
  public static Camera mainCamera;
  public static ArrayList<GameObject> objects = new ArrayList<GameObject>();
  public static ArrayList<Component> scripts = new ArrayList<Component>();
  public static Mesh[] meshes;
  public static UI UI = new UI();
  public static MusicManager audio = new MusicManager();
  static Renderer renderer;

  static double startTime;

  public static void setRenderer(Renderer rend) {
    renderer = rend;
  }

  public static void addGameObject(GameObject object) {
    object.start();
    objects.add(object);
    preRender();
  }
  public static void addScript(Component component) {
    scripts.add(component);
  }

  public static void addCollider(Collider collider) {
    CollisionData.colliders.add(collider);
  }
  
  public static void start() {
    startTime = System.currentTimeMillis();
    for (Component script : scripts) {
      script.start();
    }
    audio.start();
  }

  public static void preRender() {
    meshes = new Mesh[objects.size()];

    for (int i = 0; i < objects.size(); i++) {
      meshes[i] = objects.get(i).getWorldMesh();
    }
  }

  public static void update() {
    Time.elapsedTime = (System.currentTimeMillis() - startTime)/1000;

    for (GameObject object : objects) {
      object.update();
    }
    for (Component script : scripts) {
      script.update();
    }
  }

  public static void setShaders(FragmentShader[] shaders) {
    renderer.setShaders(shaders);
  }
}
