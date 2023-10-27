package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public class CullTriangles implements VertexTransform {
  public double[][] compute(VertexData vertexData) {
    double[][] tempVertices = new double[vertexData.vertices.length][4];
    int vertexCount = 0;
    for(int i = 0; i < vertexData.vertices.length; i += 3) {

      if(vertexData.surfaceNormals[i/3][2] < 0) {
        tempVertices[vertexCount] = vertexData.vertices[i];
        tempVertices[vertexCount+1] = vertexData.vertices[i+1];
        tempVertices[vertexCount+2] = vertexData.vertices[i+2];
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
