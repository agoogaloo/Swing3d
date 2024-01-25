package engine.rendering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
    Time.elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

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

  public static Mesh loadObjectFromFile(String fileName) {
    try {
      File file = new File("res/obj/" + fileName);
      Scanner fileReader = new Scanner(file);

      ArrayList<double[][]> triangles = new ArrayList<double[][]>();
      ArrayList<double[]> vertices = new ArrayList<double[]>();
      while (fileReader.hasNextLine()) {
        String data = fileReader.nextLine();
        String[] line = data.split(" ");
        if (line[0].equals("v")) {
          vertices.add(new double[] {
            Double.parseDouble(line[2]),
            Double.parseDouble(line[1]),
            Double.parseDouble(line[3])
          });
        }
        if (line[0].equals("f")) {
          int v1 = Integer.parseInt(line[1]) - 1;
          int v2 = Integer.parseInt(line[2]) - 1;
          int v3 = Integer.parseInt(line[3]) - 1;
          triangles.add(new double[][] {
            vertices.get(v1),
            vertices.get(v2),
            vertices.get(v3)
          });
        }
      }
      double[][][] trianglesArray = new double[triangles.size()][3][3];
      double[][] colors = new double[triangles.size()][4];
      for (int i = 0; i < trianglesArray.length; i++) {
        colors[i] = new double[] { 1, 1, 1, 1 };
        trianglesArray[i] = triangles.get(i);
      }
      fileReader.close();
      return new Mesh(trianglesArray, colors);
    } catch (FileNotFoundException e) {
      System.out.println("File could not be found");
      e.printStackTrace();

      return new Mesh(new double[][][] {
        { { 0.0, 0.0, 0.0 }, { 0.0, 1.0, 0.0 }, { 1.0, 1.0, 0.0 } }
      }, new double[][] {
        { 1, 1, 0, 0 }
      });
    }
  }
}
