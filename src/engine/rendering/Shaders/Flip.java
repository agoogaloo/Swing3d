package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class Flip extends FragmentShader {
  public void compute() {
    double[][][] backBuffer = new double[FrameData.width][FrameData.height][4];

    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];
        int x = (FrameData.width-1) - px;
        int y = (FrameData.height-1) - py;
        backBuffer[px][y] = pixel;
      }
    }

    FrameData.frameBuffer = backBuffer;
  }
}
