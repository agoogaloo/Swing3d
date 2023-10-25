package engine.shapes;

public class Vector3 {
  public int x, y, z;

    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector2 getProjected(int focalLength) {
      return new Vector2((focalLength*this.x)/(focalLength+this.z), (focalLength*this.y)/(focalLength+this.z));
    }

    public void translate(Vector3 distance) {
        this.x += distance.x;
        this.y += distance.y;
        this.z += distance.z;
    }
}
