package engine.rendering.Shaders;

import engine.rendering.FrameData;

public class DepthShader extends FragmentShader {
  boolean inverse;

  public DepthShader(boolean inverse) {
    this.inverse = inverse;
  }

  public DepthShader() {
    this(false);
  }

  public void compute() {
    for(int px = 0; px < FrameData.width; px++) {
      for(int py = 0; py < FrameData.height; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];
        double depth = (FrameData.depthMap[px][py]);
        if(depth < 1) {
          double fogDistance = 99;      
          if(depth > (fogDistance/100)) {
            depth = 1-invLerp((fogDistance)/100, 1, depth);
            depth = depth*depth*depth;
          }
          if(inverse) { depth = 1-depth; }
  
          FrameData.depthMap[px][py] = depth;
          FrameData.frameBuffer[px][py] = new double[] {
            pixel[0]*depth, pixel[1], pixel[2], pixel[3],
          };
        } else {
          FrameData.depthMap[px][py] = 0;
        }
      }
    }
  }
}
