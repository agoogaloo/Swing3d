package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public class CullTriangles implements VertexTransform {
  public double[][] compute(VertexData vertexData) {
    //TODO remove triangles from vertex data that don't need to be calculated
    boolean[] drawTriangles = new boolean[vertexData.vertices.length/3];
    for(int i = 0; i < vertexData.vertices.length; i += 3) {
      double[] normal = vertexData.surfaceNormals[i/3];
      double[] vertex = vertexData.vertices[i];

      double angle = (
        (normal[0] * (vertex[0]-vertexData.cameraPosition[0])) +
        (normal[1] * (vertex[1]-vertexData.cameraPosition[1])) +
        (normal[2] * (vertex[2]-vertexData.cameraPosition[2]))
      );

      drawTriangles[i/3] = angle < 0;
    }

    vertexData.drawTriangles = drawTriangles;
    return vertexData.vertices;
  }
}
