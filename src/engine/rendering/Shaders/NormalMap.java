package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class NormalMap extends FragmentShader {
  public void compute() {
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];
        
        double[] normal = FrameData.normalMap[px][py];
        FrameData.frameBuffer[px][py] = new double[] {
          1, -normal[0], -normal[1], -normal[2]
        };
      }
    }
  }
}
