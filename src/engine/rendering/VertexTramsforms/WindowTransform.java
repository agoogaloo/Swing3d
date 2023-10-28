package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public class WindowTransform extends VertexTransform {
  public void compute(VertexData vertexData) {
    for(int i = 0; i < vertexData.vertices.length; i++) {
      vertexData.vertices[i] = new double[] {
        vertexData.vertices[i][0]*vertexData.width, vertexData.vertices[i][1]*vertexData.height, vertexData.vertices[i][2], vertexData.vertices[i][3]
      };
    }
  }
}
