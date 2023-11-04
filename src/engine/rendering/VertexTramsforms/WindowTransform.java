package engine.rendering.VertexTramsforms;

import engine.rendering.FrameData;
import engine.rendering.VertexData;

public class WindowTransform extends VertexTransform {
  public void compute() {
    for(int i = 0; i < VertexData.vertices.length; i++) {
      VertexData.vertices[i] = new double[] {
        VertexData.vertices[i][0]*FrameData.width, VertexData.vertices[i][1]*FrameData.height, VertexData.vertices[i][2], VertexData.vertices[i][3]
      };
    }
    for(int i = 0; i < VertexData.lightPlane.length; i++) {
      VertexData.lightPlane[i] = new double[] {
        VertexData.lightPlane[i][0]*FrameData.width, VertexData.lightPlane[i][1]*FrameData.height, VertexData.lightPlane[i][2], VertexData.lightPlane[i][3]
      };
    }
  }
}
