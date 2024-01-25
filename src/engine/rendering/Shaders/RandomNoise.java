package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class RandomNoise extends FragmentShader {
  public void compute() {
    float offset = (float)elapsedTime()/10f;
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        if(FrameData.triangleAtPixel[px][py] != -1) {
          double a = Math.random();
          double r = Math.random();
          double g = Math.random();
          double b = Math.random();
          
          FrameData.frameBuffer[px][py] = new double[] {
            a, r, g, b
          };
        }
      }
    }
  }
}
