package engine.shapes;

public class Mesh {
  public double[][][] triangles;
  public double[][] colors;

  public Mesh(double[][][] triangles, double[][] colors) {
    this.triangles = triangles;
    this.colors = colors;
  }

  public void translate(Vector3 distance) {
    for(int j = 0; j < triangles.length; j++) {
      double[][] triangle = triangles[j];
      for (int k = 0; k < triangle.length; k++) {
        triangle[k][0] += distance.x;
        triangle[k][1] += distance.y;
        triangle[k][2] += distance.z;
      }
    }
  }

  public void rotate(Vector3 angles) {
    double xAngle = angles.x * 3.1415/180;
    double yAngle = angles.y * 3.1415/180;
    double zAngle = angles.z * 3.1415/180;

    double[][] xRotationMatrix = new double[][] {
      { 1, 0, 0, 0 },
      { 0, Math.cos(xAngle), Math.sin(xAngle), 0 },
      { 0, -Math.sin(xAngle), Math.cos(xAngle), 0 },
      { 0, 0, 0, 0 },
    };

    double[][] yRotationMatrix = new double[][] {
      { Math.cos(yAngle), 0, Math.sin(yAngle), 0 },
      { 0, 1, 0, 0 },
      { -Math.sin(yAngle), 0, Math.cos(yAngle), 0 },
      { 0, 0, 0, 0 },
    };

    double[][] zRotationMatrix = new double[][] {
      { Math.cos(zAngle), Math.sin(zAngle), 0, 0 },
      { -Math.sin(zAngle), Math.cos(zAngle), 0, 0 },
      { 0, 0, 1, 0 },
      { 0, 0, 0, 0 },
    };

    for(int j = 0; j < triangles.length; j++) {
      double[][] triangle = triangles[j];
      for (int k = 0; k < triangle.length; k++) {
        triangle[k] = Matrix.multiplyVectorMatrix344(triangle[k], xRotationMatrix);
        triangle[k] = Matrix.multiplyVectorMatrix344(triangle[k], yRotationMatrix);
        triangle[k] = Matrix.multiplyVectorMatrix344(triangle[k], zRotationMatrix);
      }
    }
  }

  public static Mesh copy(Mesh mesh) {
    double[][][] trianglesCopy = new double[mesh.triangles.length][3][3];
    double[][] colorsCopy = new double[mesh.triangles.length][4];
    for(int j = 0; j < mesh.triangles.length; j++) {
      double[][] triangle = mesh.triangles[j];
      for (int k = 0; k < triangle.length; k++) {
        trianglesCopy[j][k] = new double[] {
          triangle[k][0],
          triangle[k][1],
          triangle[k][2],
        };
      }
      colorsCopy[j] = mesh.colors[j];
    }
    return new Mesh(trianglesCopy, colorsCopy);
  }
}
