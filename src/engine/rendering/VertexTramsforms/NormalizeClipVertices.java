package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public class NormalizeClipVertices implements VertexTransform {
  public double[][] compute(VertexData vertexData) {
    double[][] outputVertices = new double[vertexData.vertices.length][4];
    double[][] outputNormals = new double[vertexData.surfaceNormals.length][4];
    for(int i = 0; i < vertexData.vertices.length; i++) {
      outputVertices[i] = new double[] {
        (vertexData.vertices[i][0]+1)/2, (vertexData.vertices[i][1]+1)/2, vertexData.vertices[i][2], vertexData.vertices[i][3]
      };
    }
    for(int i = 0; i < vertexData.surfaceNormals.length; i++) {
      outputNormals[i] = new double[] {
        (vertexData.surfaceNormals[i][0]+1)/2,
        (vertexData.surfaceNormals[i][1]+1)/2,
        (vertexData.surfaceNormals[i][2]+1)/2,
        (vertexData.surfaceNormals[i][3]+1)/2,
      };
    }
    vertexData.surfaceNormals = outputNormals;
    return outputVertices;
  }
}
