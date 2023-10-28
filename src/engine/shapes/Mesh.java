package engine.shapes;

public class Mesh {
  public double[][][] triangles;

  public Mesh(double[][][] triangles) {
    this.triangles = triangles;
  }

  public void translate(double[] distance) {
    for(int j = 0; j < triangles.length; j++) {
      double[][] triangle = triangles[j];
      for (int k = 0; k < triangle.length; k++) {
        triangle[k][0] += distance[0];
        triangle[k][1] += distance[1];
        triangle[k][2] += distance[2];
      }
    }
  }

  public void rotate(double[] angles) {
    double xAngle = angles[0] * 3.1415/180;
    double yAngle = angles[1] * 3.1415/180;
    double zAngle = angles[2] * 3.1415/180;

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
    for(int j = 0; j < mesh.triangles.length; j++) {
      double[][] triangle = mesh.triangles[j];
      for (int k = 0; k < triangle.length; k++) {
        trianglesCopy[j][k] = new double[] {
          triangle[k][0],
          triangle[k][1],
          triangle[k][2],
        };
      }
    }
    return new Mesh(trianglesCopy);
  }
}
