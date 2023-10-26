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
      this.vertices = vertexTransform.compute(this.vertices);
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
        //TODO draw pixels based on triangles
        frameBuffer[x][y] = new double[] {
          1, 1, 0, 0
        };
      }
    }
  }

  public void applyFragmentShaders(FragmentShader[] fragmentShaders) {
    for (FragmentShader shader : fragmentShaders) {
      this.frameBuffer = shader.compute(this.frameBuffer);
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
