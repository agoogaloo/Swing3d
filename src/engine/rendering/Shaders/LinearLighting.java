package engine.rendering.Shaders;

import engine.rendering.VertexData;

public class LinearLighting extends VertexShader {
  public void compute() {
    double[] light = new double[] { 0, 0, -1 };
    double l = Math.sqrt(light[0]*light[0] + light[1]*light[1] + light[2]*light[2]);
    light = new double[] {
      light[0]/l, light[1]/l, light[2]/l
    };

    for (int i = 0; i < VertexData.surfaceNormals.length; i++) {
      if(VertexData.drawTriangles[i]) {
        double[] normal = VertexData.surfaceNormals[i];
        double luminance = (normal[0] * light[0] + normal[1] * light[1] + normal[2] * light[2]);
        VertexData.surfaceColors[i] = new double[] {
          VertexData.surfaceColors[i][0]*luminance, VertexData.surfaceColors[i][1], VertexData.surfaceColors[i][2], VertexData.surfaceColors[i][3]
        };
      }
    }
  }
}
