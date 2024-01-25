package engine.rendering.Shaders;

import engine.rendering.VertexData;
import engine.rendering.FrameData;

public class Blank extends FragmentShader {
  public void compute() {
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        FrameData.frameBuffer[px][py] = new double[] {
          0, 0, 0, 0
        };
        FrameData.depthMap[px][py] = 0;
      }
    }
  }
}
