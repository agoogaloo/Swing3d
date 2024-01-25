package engine.rendering.Shaders;

import engine.rendering.FrameData;

public class PalletinatorShader extends FragmentShader {
  //the pallete that the screen gets confined to. 
  //currently uses this pallete https://lospec.com/palette-list/nebulaspace
  private double[][] neonPallete = new double[][] {{77, 0, 76},{143, 0, 118},{199, 0, 131},{245, 0, 120},{255, 71, 100},
  {255, 147, 147},{255, 213, 204},{255, 243, 240},{0, 2, 33},{0, 7, 105},{0, 34, 143},{0, 80, 199},{0, 139, 245},{0, 187, 255},{71, 237, 255},{147, 255, 248}};
  private double[][] bluePalette = new double[][] { {10, 64, 26},{109, 133, 44},{179, 167, 36},{230, 235, 106},
  {237, 232, 225},{167, 219, 187},  {93, 133, 140},{61, 71, 110},{50, 36, 77},{39, 20, 43},{214, 194, 186},
  {191, 150, 132},{166, 99, 114},{115, 55, 84},{69, 30, 62},{46, 15, 41}};

  private double[][] palette = neonPallete;

  public double[][][] compute(double[][][] frameBuffer, int width, int height) {
    double[][][] updatedFrame = new double[width][height][4];
    float diffR=0, diffG = 0,diffB=0;
    for (int px = 0; px < width; px++) {
      for (int py = 0; py < height; py++) {
        double[] pixel = frameBuffer[px][py];
        pixel[0]+=diffR;
        pixel[1]+=diffG;
        pixel[2]+=diffB;
        double alpha = Math.abs(pixel[0]);
        int colour = -1;
        double minDiff = 255 * 3;
        // checking what colour in the palette the pixel is closest to
        for (int i = 0; i < palette.length; i++) {
          // get the difference from each colour in the palette
          double difference = Math.abs(pixel[1] * 255 * alpha - palette[i][0]);
          difference += Math.abs(pixel[2] * 255 * alpha - palette[i][1]);
          difference += Math.abs(pixel[3] * 255 * alpha - palette[i][2]);

          // setting the colour to the one with the lowest difference

          if (difference < minDiff) {
            colour = i;
            minDiff = difference;
          }

          // System.out.println(colour);
        }
        // System.out.println(alpha);
        // setting the colour to the one in the palette
        // System.out.println(String.valueOf(pixel[1])+String.valueOf(pixel[2])+String.valueOf(pixel[3]));
        updatedFrame[px][py] = new double[] { 1, palette[colour][0] / 255, palette[colour][1] / 255,
            palette[colour][2] / 255 };

        // updatedFrame[px][py] = new double[]
        // {1,pixel[1]*alpha,pixel[2]*alpha,pixel[3]*alpha};

      }
    }
    return updatedFrame;
  }

  @Override
  public void compute() {
    for (int px = 0; px < FrameData.width; px++) {
      for (int py = 0; py < FrameData.height; py++) {
        double[] pixel = FrameData.frameBuffer[px][py];
        double alpha = Math.abs(pixel[0]);
        int colour = -1;
        double minDiff = 255 * 3;
        // checking what colour in the palette the pixel is closest to
        for (int i = 0; i < palette.length; i++) {
          // get the difference from each colour in the palette
          double difference = Math.abs(pixel[1] * 255 * alpha - palette[i][0]);
          difference += Math.abs(pixel[2] * 255 * alpha - palette[i][1]);
          difference += Math.abs(pixel[3] * 255 * alpha - palette[i][2]);

          // setting the colour to the one with the lowest difference

          if (difference < minDiff) {
            colour = i;
            minDiff = difference;
          }

          // System.out.println(colour);
        }
        // System.out.println(alpha);
        // setting the colour to the one in the palette
        // System.out.println(String.valueOf(pixel[1])+String.valueOf(pixel[2])+String.valueOf(pixel[3]));
        FrameData.frameBuffer[px][py] = new double[] { 1, palette[colour][0] / 255, palette[colour][1] / 255,
            palette[colour][2] / 255 };

        // updatedFrame[px][py] = new double[]
        // {1,pixel[1]*alpha,pixel[2]*alpha,pixel[3]*alpha};

      }
    }

  }
}
