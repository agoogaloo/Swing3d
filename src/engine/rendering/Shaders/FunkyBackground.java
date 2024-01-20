package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class FunkyBackground extends FragmentShader {
  double brightness = 0.75;

  public void compute() {
    float[] HSV = new float[3];
    Color.RGBtoHSB((int)(brightness*255), 0, 0, HSV);

    float offset = (float)elapsedTime()/20f;
    Color newColor = new Color(Color.HSBtoRGB(
      (float)(HSV[0] + offset), 
      HSV[1], HSV[2]
    ));
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
