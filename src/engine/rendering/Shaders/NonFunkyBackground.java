package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class NonFunkyBackground extends FragmentShader {
  double brightness = 0.75;
  Color deepBlue = new Color(0,34,143);
  Color deepRed = new Color(77, 0, 76);
  Color newColor = deepRed;

  public void compute() {
   
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        double depth = FrameData.depthMap[px][py];
        double[] pixel = FrameData.frameBuffer[px][py];
        
        double a = (1-depth) + (depth)*pixel[0];
        double r = (1-depth)*(newColor.getRed()/255.0) + (depth)*pixel[1];
        double g = (1-depth)*(newColor.getGreen()/255.0) + (depth)*pixel[2];
        double b = (1-depth)*(newColor.getBlue()/255.0) + (depth)*pixel[3];
        
        FrameData.frameBuffer[px][py] = new double[] {
          a, r, g, b
        };
      }
    }
  }
}
