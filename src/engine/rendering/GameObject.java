package engine.rendering;


import java.util.ArrayList;

import engine.rendering.Components.Collider;
import engine.rendering.Components.Component;
import engine.rendering.Components.Transform;
import engine.shapes.Mesh;
import engine.shapes.Vector3;

public class GameObject {
  public Transform transform;
  public ArrayList<Component> components = new ArrayList<Component>();
  public Mesh mesh;
  public Mesh worldMesh;
  public String id;

  public GameObject(Mesh mesh) {
    this(mesh, new Vector3(0, 0, 0), new Vector3(0, 0, 0));
  }

  public GameObject(Mesh mesh, Vector3 position) {
    this(mesh, position, new Vector3(0, 0, 0));
  }
  
  public GameObject(Mesh mesh, Vector3 position, Vector3 rotation) {
    this.transform = new Transform(this, position, rotation);
    this.mesh = Mesh.copy(mesh);
    this.id = this.toString();
  }

  public void addComponent(Component component) {
    components.add(component);
    component.gameObject = this;
    component.start();
  }

  public Component getComponent(Class<?> type) {
    for (Component component : components) {
      if(component.getClass() == type) {
        return component;
      }
    }
    return new Component();
  }
  public ArrayList<Component> getComponents(Class<?> type) {
    ArrayList<Component> components = new ArrayList<Component>();
    for (Component component : components) {
      if(component.getClass() == type) {
        components.add(component);
      }
    }
    return components;
  }

  public void start() {

  }
  
  public void update() {
    for (Component component : components) {
      component.update();
    }
  }

  public Mesh getWorldMesh() {
    worldMesh = Mesh.copy(mesh);
    worldMesh.translate(Vector3.halfUnit.negative());
    worldMesh.scale(transform.scale);
    worldMesh.rotate(transform.rotation);
    // worldMesh.translate(Vector3.halfUnit);
    worldMesh.translate(transform.position);

    return worldMesh;
  }
}
