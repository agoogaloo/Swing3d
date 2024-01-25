package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class EdgeShader extends FragmentShader {
  public void compute() {
    float[] HSV = new float[3];
    Color.RGBtoHSB(255, 0, 0, HSV);

    float offset = (float)elapsedTime()/5f;
    Color newColor = new Color(Color.HSBtoRGB(
      (float)(HSV[0] + offset), 
      HSV[1], HSV[2]
    ));

    for(int px = 1; px < FrameData.width-1; px++) {
      for(int py = 1; py < FrameData.height-1; py++) {
        if(FrameData.edgeBuffer[px][py] == 1) {
          FrameData.frameBuffer[px][py] = new double[] {
            1,
            newColor.getRed()/255.0,
            newColor.getGreen()/255.0,
            newColor.getBlue()/255.0,
          };
        } 
      }
    }
  }
}
