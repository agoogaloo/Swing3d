package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public class CullTriangles extends VertexTransform {
  public void compute() {
    //TODO remove triangles from vertex data that don't need to be calculated
    for(int i = 0; i < VertexData.vertices.length; i += 3) {
      double[] normal = VertexData.surfaceNormals[i/3];
      double[] vertex = VertexData.vertices[i];

      double angle = (
        (normal[0] * (vertex[0]-VertexData.cameraPosition[0])) +
        (normal[1] * (vertex[1]-VertexData.cameraPosition[1])) +
        (normal[2] * (vertex[2]-VertexData.cameraPosition[2]))
      );

      VertexData.drawTriangles[i/3] = (angle < 0);
    }
  }
}
