package engine.rendering.Shaders;

import java.awt.Color;

import engine.rendering.FrameData;

public class EdgeShader extends FragmentShader {
  Color color;
  boolean setColor = false;

  public EdgeShader(Color color) {
    this.color = color;
    setColor = true;
  }

  public EdgeShader() {}

  public void compute() {
    if(!setColor) {
      float[] HSV = new float[3];
      Color.RGBtoHSB(255, 0, 0, HSV);
  
      float offset = (float)elapsedTime()/5f;
      color = new Color(Color.HSBtoRGB(
        (float)(HSV[0] + offset), 
        HSV[1], HSV[2]
      ));
    }

    for(int px = 1; px < FrameData.width-1; px++) {
      for(int py = 1; py < FrameData.height-1; py++) {
        if(FrameData.edgeBuffer[px][py] == 1) {
          FrameData.frameBuffer[px][py] = new double[] {
            color.getAlpha(),
            color.getRed()/255.0,
            color.getGreen()/255.0,
            color.getBlue()/255.0,
          };
        } 
      }
    }
  }
}
