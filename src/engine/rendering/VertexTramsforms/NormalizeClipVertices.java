package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public class NormalizeClipVertices extends VertexTransform {
  public void compute() {
    for(int i = 0; i < VertexData.vertices.length; i++) {
      VertexData.vertices[i] = new double[] {
        (VertexData.vertices[i][0]+1)/2, (VertexData.vertices[i][1]+1)/2, VertexData.vertices[i][2], VertexData.vertices[i][3]
      };
    }
    for(int i = 0; i < VertexData.lightPlane.length; i++) {
      VertexData.lightPlane[i] = new double[] {
        (VertexData.lightPlane[i][0]+1)/2, (VertexData.lightPlane[i][1]+1)/2, VertexData.lightPlane[i][2], VertexData.lightPlane[i][3]
      };
    }
  }
}
