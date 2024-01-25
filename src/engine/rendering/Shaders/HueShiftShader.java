package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class HueShiftShader extends FragmentShader {
  public void compute() {
    float offset = (float)elapsedTime()/10f;
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];
        float[] HSV = new float[3];
        Color.RGBtoHSB((int)(pixel[1]*255), (int)(pixel[2]*255), (int)(pixel[3]*255), HSV);
        
        Color newColor = new Color(Color.HSBtoRGB(
          (float)(HSV[0] + offset), 
          HSV[1], HSV[2]
        ));
        
        double a = pixel[0];
        double r = newColor.getRed()/255.0;
        double g = newColor.getGreen()/255.0;
        double b = newColor.getBlue()/255.0;
        
        FrameData.frameBuffer[px][py] = new double[] {
          a, r, g, b
        };
      }
    }
  }
}
