package engine.rendering.Shaders;

import engine.rendering.FrameData;

public class TVStatic extends FragmentShader {
  public void compute() {
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        if(FrameData.triangleAtPixel[px][py] == -1) {
          double a = Math.random();
          
          FrameData.frameBuffer[px][py] = new double[] {
            a, a, a, a
          };
        }
      }
    }
  }
}
