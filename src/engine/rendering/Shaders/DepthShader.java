package engine.rendering.Shaders;

public class DepthShader extends FragmentShader {
  public double[][][] compute(double[][][] frameBuffer, double[][] depthBuffer, int width, int height) {
    double[][][] updatedFrame = new double[width][height][4];
    for(int px = 0; px < width; px++) {
      for(int py = 0; py < height; py++) {
        double[] pixel = frameBuffer[px][py];
        double depth = (1-depthBuffer[px][py]);

        depth = depth*depth*depth;
        updatedFrame[px][py] = new double[] {
          pixel[0]*depth, pixel[1], pixel[2], pixel[3],
        };
      }
    }
    return updatedFrame;
  }
}
