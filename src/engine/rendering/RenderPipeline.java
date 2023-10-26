package engine.rendering;

import java.awt.image.BufferedImage;

import engine.rendering.Shaders.FragmentShader;
import engine.rendering.Shaders.VertexShader;
import engine.rendering.VertexTramsforms.VertexTransform;

public class RenderPipeline {
  double[][] vertices;
  double[][][] frameBuffer;
  int width, height;
  
  public void initialize(double[][] vertices, int width, int height) {
    this.vertices = vertices;
    this.width = width;
    this.height = height;
  }

  public void applyVertexTransformations(VertexTransform[] vertexTransforms) {
    for (VertexTransform vertexTransform : vertexTransforms) {
      this.vertices = vertexTransform.compute(this.vertices, this.width, this.height);
    }
  }

  public void applyVertexShaders(VertexShader[] vertexShaders) {
    for (VertexShader shader : vertexShaders) {
      this.vertices = shader.compute(this.vertices);
    }
  }
  public void scan() {
    frameBuffer = new double[width][height][4];
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        boolean pixelInTriangle = false;
        for(int i = 0; i < vertices.length; i += 3) {
          double[] v0 = vertices[i];
          double[] v1 = vertices[i+1];
          double[] v2 = vertices[i+2];
          if(PointInTriangle(new double[] { x, y }, v0, v1, v2)) {
            pixelInTriangle = true;
            break;
          }
        }
        if(pixelInTriangle) {
          frameBuffer[x][y] = new double[] {
            1, 1, 0, 0
          };
        } else {
          frameBuffer[x][y] = new double[] {
            1, 0, 0, 0
          };
        }
      }
    }
  }

  private double sign (double[] v0, double[] v1, double[] v2) {
    return ((v0[0] - v2[0]) * (v1[1] - v2[1]) - (v1[0] - v2[0]) * (v0[1] - v2[1]));
  }

  private boolean PointInTriangle (double[] vt, double[] v0, double[] v1, double[] v2) {
    double d0, d1, d2;
    boolean has_neg, has_pos;

    d0 = sign(vt, v0, v1);
    d1 = sign(vt, v1, v2);
    d2 = sign(vt, v2, v0);

    has_neg = (d0 < 0) || (d1 < 0) || (d2 < 0);
    has_pos = (d0 > 0) || (d1 > 0) || (d2 > 0);

    return !(has_neg && has_pos);
  }

  public void applyFragmentShaders(FragmentShader[] fragmentShaders) {
    for (FragmentShader shader : fragmentShaders) {
      this.frameBuffer = shader.compute(this.frameBuffer, width, height);
    }
  }

  public void display(BufferedImage frame) {
    int[] stackedFrame = new int[width*height];
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        double[] color = frameBuffer[x][y];
        stackedFrame[y*width+x] = getIntFromColor((int)(color[0]*255), (int)(color[1]*255), (int)(color[2]*255), (int)(color[3]*255));
      }
    }
    frame.setRGB(0, 0, width, height, stackedFrame, 0, width);
  }

  int getIntFromColor(int alpha, int red, int green, int blue){
    alpha = (alpha << 24) & 0xFF000000; //Shift red 16-bits and mask out other stuff
    red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
    green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
    blue = blue & 0x000000FF; //Mask out anything not blue.

    return alpha | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
  }
}
