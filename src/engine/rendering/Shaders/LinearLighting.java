package engine.rendering.Shaders;

import engine.rendering.VertexData;

public class LinearLighting implements VertexShader {
  public double[][] compute(VertexData vertexData) {
    double[] light = new double[] { 0, 0, -1 };
    double l = Math.sqrt(light[0]*light[0] + light[1]*light[1] + light[2]*light[2]);
    light = new double[] {
      light[0]/l, light[1]/l, light[2]/l
    };

    for (int i = 0; i < vertexData.surfaceColors.length; i++) {
      if(vertexData.drawTriangles[i]) {
        double[] normal = vertexData.surfaceNormals[i];
        double color = (normal[0] * light[0] + normal[1] * light[1] + normal[2] * light[2]);
        vertexData.surfaceColors[i] = new double[] {
          1, color, color, color
        };
      }
    }

    return vertexData.vertices;
  }
}
