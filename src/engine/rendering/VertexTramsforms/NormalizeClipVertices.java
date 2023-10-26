package engine.rendering.VertexTramsforms;

public class NormalizeClipVertices implements VertexTransform {
  public double[][] compute(double[][] vertices, int width, int height) {
    double[][] outputVertices = new double[vertices.length][4];
    for(int i = 0; i < vertices.length; i++) {
      double w = vertices[i][3];
      outputVertices[i] = new double[] {
        (vertices[i][0]/w +1)/2, (vertices[i][1]/-w +1)/2, (vertices[i][2]/w +1)/2, w
      };
    }
    return outputVertices;
  }
}
