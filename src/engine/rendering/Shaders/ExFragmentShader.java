package engine.rendering.Shaders;

public class ExFragmentShader implements FragmentShader {
  public double[][][] compute(double[][][] pixels, int width, int height) {
    double[][][] updatedFrame = new double[width][height][4];
    for(int px = 0; px < width; px++) {
      for(int py = 0; py < height; py++) {
        double[] pixel = pixels[px][py];

        //normalize x and y to be from 0 to 1, 
        //0 is the left/top and 1 is the right/bottom
        double x = ((double)px/height);
        double y = ((double)py/height);
  
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
        updatedFrame[px][py] = new double[] {
          a, r, g, b
        };
      }
    }
    return updatedFrame;
  }
}
