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
        triangleLuminance[i] = -(
          normal[0] * VertexData.lightDirection[0] + 
          normal[1] * VertexData.lightDirection[1] + 
          normal[2] * VertexData.lightDirection[2]
        );
      }
    }

    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        int index = FrameData.triangleAtPixel[px][py];
        if(index != -1) {
          double[] pixel = FrameData.frameBuffer[px][py];
          double depth = FrameData.depthMap[px][py];
          double luminance = triangleLuminance[index];
          double dist = Vector.distToPlane(
            VertexData.lightDirection, VertexData.lightPlane[0], 
            new double[] { px, py, depth }  
          );
          dist = invLerp(1, 0.75, ((dist-1)*10));
  
          FrameData.frameBuffer[px][py] = new double[] {
            pixel[0]*dist*luminance, 
            pixel[1], pixel[2], pixel[3],
          };
        }
      }
    }
  }
}
