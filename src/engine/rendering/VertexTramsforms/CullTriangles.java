package engine.rendering.VertexTramsforms;

public class CullTriangles implements VertexTransform {
  public double[][] compute(double[][] vertices, double[][] normals, int width, int height) {
    double[][] tempVertices = new double[vertices.length][4];
    int vertexCount = 0;
    for(int i = 0; i < vertices.length; i += 3) {

      if(normals[i/3][2] < 0) {
        tempVertices[vertexCount] = vertices[i];
        tempVertices[vertexCount+1] = vertices[i+1];
        tempVertices[vertexCount+2] = vertices[i+2];
        vertexCount += 3;
      }
    }
    double[][] outputVertices = new double[vertexCount][4];
    for (int i = 0; i < vertexCount; i++) {
      outputVertices[i] = tempVertices[i];
    }
    return outputVertices;
  }
}
