package engine.rendering.Shaders;

import engine.rendering.FrameData;
import engine.rendering.VertexData;
import engine.shapes.Vector;

public class InvertColors extends FragmentShader {
  public void compute() {
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];
          
        FrameData.frameBuffer[px][py] = new double[] {
          pixel[0],
          1-pixel[1],
          1-pixel[2],
          1-pixel[3],
        };
      }
    }
  }
}
