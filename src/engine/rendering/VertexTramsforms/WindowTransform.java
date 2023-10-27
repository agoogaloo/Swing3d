package engine.rendering.VertexTramsforms;

public class WindowTransform implements VertexTransform {
  int width, height;
  public double[][] compute(double[][] vertices, double[][] normals, int width, int height) {
    double[][] outputVertices = new double[vertices.length][4];
    for(int i = 0; i < vertices.length; i++) {
      outputVertices[i] = new double[] {
        vertices[i][0]*width, vertices[i][1]*height, vertices[i][2], vertices[i][3]
      };
    }
    return outputVertices;
  }
}
