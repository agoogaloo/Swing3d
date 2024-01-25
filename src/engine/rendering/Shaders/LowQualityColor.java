package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class LowQualityColor extends FragmentShader {
  int colorValues = 16;

  public LowQualityColor(int colors) {
    this.colorValues = colors;
  }

  public void compute() {
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];
        
        double a = (double)((int)(pixel[0]*colorValues)/(double)colorValues);
        double r = (double)((int)(pixel[1]*colorValues)/(double)colorValues);
        double g = (double)((int)(pixel[2]*colorValues)/(double)colorValues);
        double b = (double)((int)(pixel[3]*colorValues)/(double)colorValues);
        
        FrameData.frameBuffer[px][py] = new double[] {
          a, r, g, b
        };
      }
    }
  }
}

