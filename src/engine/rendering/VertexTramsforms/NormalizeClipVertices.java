package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public class NormalizeClipVertices implements VertexTransform {
  public double[][] compute(VertexData vertexData) {
    double[][] outputVertices = new double[vertexData.vertices.length][4];
    for(int i = 0; i < vertexData.vertices.length; i++) {
      outputVertices[i] = new double[] {
        (vertexData.vertices[i][0]+1)/2, (vertexData.vertices[i][1]+1)/2, vertexData.vertices[i][2], vertexData.vertices[i][3]
      };
    }
    
    return outputVertices;
  }
}
