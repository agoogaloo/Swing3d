package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public class CullTriangles implements VertexTransform {
  public double[][] compute(VertexData vertexData) {
    double[][] tempVertices = new double[vertexData.vertices.length][4];
    int vertexCount = 0;
    for(int i = 0; i < vertexData.vertices.length; i += 3) {
      double[] normal = vertexData.surfaceNormals[i/3];
      double[] vertex = vertexData.vertices[i];

      double angle = 
        (normal[0] * (vertex[0]-vertexData.cameraPosition[0])) +
        (normal[1] * (vertex[1]-vertexData.cameraPosition[1])) +
        (normal[2] * (vertex[2]-vertexData.cameraPosition[2]));

      if(angle < 0) {
        tempVertices[vertexCount] = vertexData.vertices[i];
        tempVertices[vertexCount+1] = vertexData.vertices[i+1];
        tempVertices[vertexCount+2] = vertexData.vertices[i+2];
        vertexCount += 3;
      }
    }
    double[][] outputVertices = new double[vertexCount][4];
    double[][] outputNormals = new double[vertexCount/3][4];
    for (int i = 0; i < vertexCount; i++) {
      outputVertices[i] = tempVertices[i];
      outputNormals[i/3] = vertexData.surfaceNormals[i/3];
    }
    vertexData.surfaceNormals = outputNormals;
    return outputVertices;
  }
}
