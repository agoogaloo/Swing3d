package engine.shapes;

public class Vector3 {
  double x, y, z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector2 getProjected(double focalLength) {
      return new Vector2((focalLength*this.x)/(focalLength+this.z), (focalLength*this.y)/(focalLength+this.z));
    }

    public void translate(Vector3 distance) {
        this.x += distance.x;
        this.y += distance.y;
        this.z += distance.z;
    }
}
