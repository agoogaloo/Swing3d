package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class EdgeDetection extends FragmentShader {
  public void compute() {
    FrameData.edgeBuffer = new int[FrameData.width][FrameData.height];

    for(int px = 1; px < FrameData.width-1; px++) {
      for(int py = 1; py < FrameData.height-1; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];
        if(
          !pixelsEqual(FrameData.frameBuffer[px+1][py], pixel) ||
          !pixelsEqual(FrameData.frameBuffer[px-1][py], pixel) ||
          !pixelsEqual(FrameData.frameBuffer[px][py+1], pixel) ||
          !pixelsEqual(FrameData.frameBuffer[px][py-1], pixel)
        ) {
          FrameData.edgeBuffer[px][py] = 1;
        }
      }
    }
  }

  boolean pixelsEqual(double[] p1, double[] p2) {
    return (p1[0] == p2[0]) && (p1[1] == p2[1]) && (p1[2] == p2[2]) && (p1[3] == p2[3]);
  }
}
