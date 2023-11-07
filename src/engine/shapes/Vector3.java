package engine.shapes;

public class Vector3 {
  public double x, y, z;
  public static final Vector3 origin = new Vector3(0, 0, 0);
  public static final Vector3 unit = new Vector3(1, 1, 1);
  public static final Vector3 halfUnit = new Vector3(0.5, 0.5, 0.5);

  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public void translate(Vector3 distance) {
    this.x += distance.x;
    this.y += distance.y;
    this.z += distance.z;
  }

  public Vector3 negative() {
    return new Vector3(-x, -y, -z);
  }

  public double[] toDouble() {
    return new double[] { x, y, z };
  }
}
