package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class BlurShader extends FragmentShader {
  public void compute() {
    for(int px = 1; px < FrameData.width-1; px++) {
      for(int py = 1; py < FrameData.height-1; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];

        double[] p1 = FrameData.frameBuffer[px+1][py];
        double[] p2 = FrameData.frameBuffer[px-1][py];
        double[] p3 = FrameData.frameBuffer[px][py+1];
        double[] p4 = FrameData.frameBuffer[px][py-1];

        FrameData.frameBuffer[px][py] = new double[] {
          (pixel[0]+p1[0]+p2[0]+p3[0]+p4[0])/5,
          (pixel[1]+p1[1]+p2[1]+p3[1]+p4[1])/5,
          (pixel[2]+p1[2]+p2[2]+p3[2]+p4[2])/5,
          (pixel[3]+p1[3]+p2[3]+p3[3]+p4[3])/5,
        };
      }
    }
  }

  boolean pixelsEqual(double[] p1, double[] p2) {
    return (p1[0] == p2[0]) && (p1[1] == p2[1]) && (p1[2] == p2[2]) && (p1[3] == p2[3]);
  }
}

