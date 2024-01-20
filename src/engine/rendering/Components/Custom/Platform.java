package engine.rendering.Components.Custom;

import engine.rendering.Components.BoxCollider;
import engine.rendering.Components.Component;
import engine.shapes.MeshColors;
import engine.shapes.Vector3;

public class Platform extends Component {
  Vector3 pos, size;
  boolean hazard;

  public Platform(Vector3 position, Vector3 size, boolean hazard) {
    this.pos = position; this.size = size; this.hazard = hazard;
  }
  
  public void start() {
    gameObject.transform.translate(pos);
    gameObject.transform.setScale(size);
    int layer = 0;
    if(hazard) { 
      layer = 1; 
      gameObject.mesh.colors = MeshColors.red;
    } else {
      gameObject.mesh.colors = MeshColors.blue;
    }

    gameObject.addComponent(new BoxCollider(size, new Vector3(0, 0, 0), layer));
  }

  public void update() {
  }
}
