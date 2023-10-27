package engine.rendering.VertexTramsforms;

public class ClipVertices implements VertexTransform {
  public double[][] compute(double[][] vertices, int width, int height) {
    double[][] outputVertices = new double[vertices.length][4];
    for(int i = 0; i < vertices.length; i++) {
      outputVertices[i] = new double[] {
        Math.min(vertices[i][0], 1), Math.min(vertices[i][1], 1), vertices[i][2], vertices[i][3]
      };
    }
    return outputVertices;
  }
}
