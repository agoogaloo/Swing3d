package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public class NormalizeClipVertices extends VertexTransform {
  public void compute(VertexData vertexData) {
    for(int i = 0; i < vertexData.vertices.length; i++) {
      vertexData.vertices[i] = new double[] {
        (vertexData.vertices[i][0]+1)/2, (vertexData.vertices[i][1]+1)/2, vertexData.vertices[i][2], vertexData.vertices[i][3]
      };
    }
  }
}
