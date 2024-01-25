package engine.rendering.Shaders;

import engine.rendering.FrameData;
import engine.rendering.VertexData;
import engine.shapes.Vector;

public class RotatingInvert extends FragmentShader {
  int sector = 0;
  public void compute() {
    double x = Math.sin(elapsedTime());
    double y = Math.cos(elapsedTime());
    x = ((double)FrameData.width/2f) + 10*x;
    y = ((double)FrameData.height/2f) + 10*y;

    double m = (x-((double)FrameData.width/2f))/(y-(double)FrameData.height/2f);
    double b = y-(m*x);
    
    if(sector == 0 && m <= 0) { sector = 1; }
    if(sector == 1 && m >= 0) { sector = 2; }
    if(sector == 2 && m <= 0) { sector = 3; }
    if(sector == 3 && m >= 0) { sector = 0; }

    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        if(
          (sector == 0 && py >= (int)(m*(double)px+b)) ||
          (sector == 1 && m <= 0 && py <= (int)(m*(double)px+b)) ||
          (sector == 2 && py <= (int)(m*(double)px+b)) ||
          (sector == 3 && py >= (int)(m*(double)px+b)) 
        ) {
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
}
