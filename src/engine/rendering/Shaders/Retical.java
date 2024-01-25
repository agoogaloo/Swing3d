package engine.rendering.Shaders;

import engine.rendering.FrameData;
import engine.rendering.VertexData;
import engine.shapes.Vector;

public class Retical extends FragmentShader {
  double r = 5;

  public void compute() {
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        double x = (invLerp(0, FrameData.width, px)*2)-1;
        double y = ((invLerp(0, FrameData.height, py)*2)-1) * ((double)FrameData.height/(double)FrameData.width);

        if(x*x + y*y <= r/100000) {
          double a = 1;
          double r = 0;
          double g = 1;
          double b = 0.25;
          
          FrameData.frameBuffer[px][py] = new double[] {
            a, r, g, b
          };
        }
      }
    }
  }
}
