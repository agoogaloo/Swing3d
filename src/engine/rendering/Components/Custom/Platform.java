package engine.rendering.Components.Custom;

import engine.rendering.Components.BoxCollider;
import engine.rendering.Components.Component;
import engine.shapes.MeshColors;
import engine.shapes.Vector3;

public class Platform extends Component {
  Vector3 pos, size;
  int layer;

  public Platform(Vector3 position, Vector3 size, int layer) {
    this.pos = position; this.size = size; this.layer = layer;
  }
  
  public void start() {
    gameObject.transform.translate(pos);
    gameObject.transform.setScale(size);
    if(layer == 0) { 
      gameObject.mesh.colors = MeshColors.lightTeal;
    } else if(layer == 1) { 
      gameObject.mesh.colors = MeshColors.pink;
    } else if(layer == 2) { 
      gameObject.mesh.colors = MeshColors.green;
    } 

    gameObject.addComponent(new BoxCollider(size, new Vector3(0, 0, 0), layer));
  }

  public void update() {
  }
}
