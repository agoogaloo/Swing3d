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
}
