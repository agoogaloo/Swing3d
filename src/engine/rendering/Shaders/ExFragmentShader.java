package engine.rendering.Shaders;

import engine.rendering.VertexData;
import engine.rendering.FrameData;

public class ExFragmentShader extends FragmentShader {
  public void compute() {
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];
        if(pixel[1] == 1) {
          //normalize x and y to be from 0 to 1, 
          //0 is the left/top and 1 is the right/bottom
          double x = ((double)px/FrameData.height);
          double y = ((double)py/FrameData.height);
    
          //compute values for a, r, g, and b
          //a is the opacity
          //values range from 0 to 1
          double a = 1;
          double r = (
            (Math.sin(x*(4*3.1415))+1)/2
          );
          double g = (
            (Math.sin(y*(4*3.1415))+1)/2
          );
          double b = (
            (Math.cos(x*(4*3.1415))+1)/2
          );

          //write the rgb values to the pixel
          FrameData.frameBuffer[px][py] = new double[] {
            a, r, g, b
          };
        }
      }
    }
  }
}
