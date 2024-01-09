package engine.rendering;

import java.util.ArrayList;

import engine.CollisionData;
import engine.rendering.Components.Component;
import engine.shapes.Mesh;

public class Scene {
  public static Camera mainCamera;
  public static ArrayList<GameObject> objects = new ArrayList<GameObject>();
  public static ArrayList<Component> scripts = new ArrayList<Component>();

  static double startTime;
  public static double elapsedTime;

  public static void addGameObject(GameObject object) {
    objects.add(object);
    preRender();
    object.start();
  }
  public static void addScript(Component component) {
    scripts.add(component);
    component.start();
  }

  public static void start() {
    startTime = System.currentTimeMillis();
  }

  public static void preRender() {
    CollisionData.meshes = new Mesh[objects.size()];

    for (int i = 0; i < objects.size(); i++) {
      CollisionData.meshes[i] = objects.get(i).getWorldMesh();
    }
  }

  public static void update() {
    elapsedTime = (System.currentTimeMillis() - startTime)/1000;

    for (GameObject object : objects) {
      object.update();
    }
    for (Component script : scripts) {
      script.update();
    }
  }
}
