package engine.rendering.Shaders;

import engine.rendering.FrameData;

public class BayerDither extends FragmentShader {
  // the pallete that the screen gets confined to.
  // currently uses this pallete https://lospec.com/palette-list/nebulaspace
  private double[][] neonPallete = new double[][] { { 77, 0, 76 }, { 143, 0, 118 }, { 199, 0, 131 }, { 245, 0, 120 },
      { 255, 71, 100 },
      { 255, 147, 147 }, { 255, 213, 204 }, { 255, 243, 240 }, { 0, 2, 33 }, { 0, 7, 105 }, { 0, 34, 143 },
      { 0, 80, 199 }, { 0, 139, 245 }, { 0, 187, 255 }, { 71, 237, 255 }, { 147, 255, 248 } };

  private double[][] bluePalette = new double[][] { { 10, 64, 26 }, { 109, 133, 44 }, { 179, 167, 36 },
      { 230, 235, 106 },
      { 237, 232, 225 }, { 167, 219, 187 }, { 93, 133, 140 }, { 61, 71, 110 }, { 50, 36, 77 }, { 39, 20, 43 },
      { 214, 194, 186 },
      { 191, 150, 132 }, { 166, 99, 114 }, { 115, 55, 84 }, { 69, 30, 62 }, { 46, 15, 41 } };

  private double[][] matrix4 = {
      { 0, 8, 2, 10 },
      { 12, 4, 14, 6 },
      { 3, 11, 1, 9 },
      { 15, 7, 13, 5 }
  };
  private double[][] matrix = {
      { 0, 32, 8, 40, 2, 34, 10, 42 },
      { 48, 16, 56, 24, 50, 18, 58, 26 },
      { 12, 44, 4, 36, 14, 46, 6, 38 },
      { 60, 28, 52, 20, 62, 30, 54, 22 },
      { 3, 35, 11, 43, 1, 33, 9, 41 },
      { 51, 19, 59, 27, 49, 17, 57, 25 },
      { 15, 47, 7, 39, 13, 45, 5, 37 },
      { 63, 31, 55, 23, 61, 29, 53, 21 }
  };
  private int matrixSize = 8;

  private double[][] palette = neonPallete;

  private void addDiff(int x, int y, double r, double g, double b) {

    if (x < 0 || x >= FrameData.width || y >= FrameData.height || y < 0) {
      return;
    }

    FrameData.frameBuffer[x][y][0] += r;
    FrameData.frameBuffer[x][y][1] += g;
    FrameData.frameBuffer[x][y][2] += b;

  }

  @Override
  public void compute() {
    for (int py = 0; py < FrameData.height; py++) {
      for (int px = 0; px < FrameData.width; px++) {
        double[] pixel = FrameData.frameBuffer[px][py];

        colourDither(pixel, px, py);
        // System.out.println(alpha);
        // setting the colour to the one in the palette
        // System.out.println(String.valueOf(pixel[1])+String.valueOf(pixel[2])+String.valueOf(pixel[3]));

        // updatedFrame[px][py] = new double[]
        // {1,pixel[1]*alpha,pixel[2]*alpha,pixel[3]*alpha};

      }
    }

  }

  private void oneBitDither(double[] pixel, int px, int py) {
    double alpha = pixel[0];
    double brightness = (pixel[1] + pixel[2] + pixel[3]) * alpha / 3;

    double matrixBrighness = matrix[(px % matrixSize)][(py % matrixSize)] / (matrixSize * matrixSize);

    if (brightness > matrixBrighness) {
      FrameData.frameBuffer[px][py] = new double[] { 1, 1, 1, 1 };
    } else {
      FrameData.frameBuffer[px][py] = new double[] { 1, 0, 0, 0 };
    }

  }

  private void colourDither(double[] pixel, int px, int py) {
    double alpha = pixel[0];
    int colour = -1, nextColour = -1;
    double dist = 9999999, nextDist = 999999;

    // getting the 2 closest colours
    for (int i = 0; i < palette.length; i++) {
      // get the difference from each colour in the palette
      double difference = Math.abs(pixel[1] * 255 * alpha - palette[i][0]);
      difference += Math.abs(pixel[2] * 255 * alpha - palette[i][1]);
      difference += Math.abs(pixel[3] * 255 * alpha - palette[i][2]);

      // setting the colour to the one with the lowest difference

      if (difference < dist) {
        colour = i;
        dist = difference;
      } else if (difference < nextDist) {
        nextColour = i;
        nextDist = difference;
      }

    }
    double matrixBrighness = matrix[(px % matrixSize)][(py % matrixSize)] / (matrixSize * matrixSize);

    if (dist / 255 < matrixBrighness) {

      FrameData.frameBuffer[px][py] = new double[] { 1, palette[colour][0] / 255f, palette[colour][1] / 255f,
          palette[colour][2] / 255f };
    } else {
      FrameData.frameBuffer[px][py] = new double[] { 1, palette[nextColour][0] / 255f, palette[nextColour][1] / 255f,
          palette[nextColour][2] / 255f };
    }

  }

}
