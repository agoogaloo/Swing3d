package engine.rendering.Shaders;

import engine.rendering.FrameData;
import engine.rendering.VertexData;
import engine.shapes.Vector;

public class LinearLighting extends FragmentShader {
  public void compute() {

    double[] triangleLuminance = new double[VertexData.surfaceNormals.length];
    for (int i = 0; i < VertexData.surfaceNormals.length; i++) {
      if(VertexData.drawTriangles[i]) {
        double[] normal = VertexData.surfaceNormals[i];
        double luminance = (
          normal[0] * VertexData.lightDirection[0] + 
          normal[1] * VertexData.lightDirection[1] + 
          normal[2] * VertexData.lightDirection[2]
        );
        if(luminance < 0) { luminance = 0; }
        luminance += 0.5;
        if(luminance > 1) { luminance = 1; }

        triangleLuminance[i] = luminance;
      }
    }

    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        int index = FrameData.triangleAtPixel[px][py];
        if(index != -1) {
          double[] pixel = FrameData.frameBuffer[px][py];
          double luminance = triangleLuminance[index];
  
          FrameData.frameBuffer[px][py] = new double[] {
            pixel[0]*luminance,
            pixel[1], pixel[2], pixel[3], 
          };
        }
      }
    }
  }
}
