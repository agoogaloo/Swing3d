package engine.rendering.Shaders;

public class DepthShader extends FragmentShader {
  public double[][][] compute(double[][][] frameBuffer, double[][] depthBuffer, int width, int height) {
    double[][][] updatedFrame = new double[width][height][4];
    for(int px = 0; px < width; px++) {
      for(int py = 0; py < height; py++) {
        double[] pixel = frameBuffer[px][py];
        double depth = (depthBuffer[px][py]);
        if(depth < 1) {
          double fogDistance = 3;      
          if(depth > 1-(fogDistance/100)) {
            depth = 1-invLerp(1-(fogDistance/100), 1, depth);
            depth = depth*depth*depth;
          }

          updatedFrame[px][py] = new double[] {
            pixel[0]*depth, pixel[1], pixel[2], pixel[3],
          };
        } else {
          updatedFrame[px][py] = new double[] {
            0, 0, 0, 0,
          };
        }
      }
    }
    return updatedFrame;
  }
}
