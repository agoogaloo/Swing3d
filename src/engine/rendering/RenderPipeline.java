package engine.rendering;

import java.awt.image.BufferedImage;

import engine.rendering.Shaders.FragmentShader;
import engine.rendering.Shaders.VertexShader;
import engine.rendering.VertexTramsforms.VertexTransform;
import engine.shapes.Vector2;

public class RenderPipeline {
  double[][] worldVertices;
  double[][] vertices;
  double[][][] frameBuffer;
  int width, height;
  
  public void initialize(double[][] vertices, int width, int height) {
    this.worldVertices = vertices;
    this.vertices = new double[worldVertices.length][4];
    this.width = width;
    this.height = height;
  }

  public void projectVertices() {
    double near = 0.1;
    double far = 1000;
    double fovRad = 90/2 * (3.1415/180);
    double fov = 1/Math.tan(fovRad);
    double aspectRatio = (double)height/(double)width;

    double[][] projectionMatrix = new double[][] {
      { aspectRatio * fov, 0, 0, 0 },
      { 0, fov, 0, 0 },
      { 0, 0, far/(far-near), 1 },
      { 0, 0, (-far * near)/(far-near), 0 },
    };

    for(int i = 0; i < worldVertices.length; i++) {
      vertices[i] = multiplyVectorMatrix4(worldVertices[i], projectionMatrix);
    }    
    return;
  }

  private double[] multiplyVectorMatrix4(double[] vector, double[][] matrix) {
    double x = vector[0] * matrix[0][0] + vector[1] * matrix[1][0] + vector[2] * matrix[2][0] + matrix[3][0];
    double y = vector[0] * matrix[0][1] + vector[1] * matrix[1][1] + vector[2] * matrix[2][1] + matrix[3][1];
    double z = vector[0] * matrix[0][2] + vector[1] * matrix[1][2] + vector[2] * matrix[2][2] + matrix[3][2];
    double w = vector[0] * matrix[0][3] + vector[1] * matrix[1][3] + vector[2] * matrix[2][3] + matrix[3][3];
    if(w != 0) {
      x /= w; y /= w; z /= w; 
    }
    return new double[] { x, y, z,w };
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
  public void scan(boolean drawEdges, boolean fill) {
    frameBuffer = new double[width][height][4];
    if(fill) {
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
    if(drawEdges) {
      for(int i = 0; i < vertices.length; i += 3) {
        double[] v0 = vertices[i];
        double[] v1 = vertices[i+1];
        double[] v2 = vertices[i+2];

        drawLine(v1, v0, frameBuffer);
        drawLine(v2, v1, frameBuffer);
        drawLine(v0, v2, frameBuffer);
      }
    }
  }

  private void drawLine(double[] vertex1, double[] vertex2, double[][][] frameBuffer) {
      Vector2 v0 = new Vector2((int)vertex1[0], (int)vertex1[1]);
      Vector2 v1 = new Vector2((int)vertex2[0], (int)vertex2[1]);

      //implementation of Bresenham's line drawing algorithm
      int dx = Math.abs(v1.x - v0.x);
      int sx = v0.x < v1.x ? 1 : -1;
      int dy = -Math.abs(v1.y - v0.y);
      int sy = v0.y < v1.y ? 1 : -1;
      int error = dx + dy;

      while(true) {
          if(v0.x < width && v0.y < height) {
            frameBuffer[v0.x][v0.y] = new double[] {
              1, 0, 1, 0
            };
          }
          if(v0.x == v1.x && v0.y == v1.y) break;
          int e2 = 2 * error;
          if(e2 >= dy) {
              if(v0.x == v1.x) break;
              error = error + dy;
              v0.x = v0.x + sx;
          }
          if(e2 <= dx) {
              if(v0.y == v1.y) break;
              error = error + dx;
              v0.y = v0.y + sy;
          }
      }
  }

  private double sign (double[] v0, double[] v1, double[] v2) {
    return ((v0[0] - v2[0]) * (v1[1] - v2[1]) - (v1[0] - v2[0]) * (v0[1] - v2[1]));
  }

  private boolean PointInTriangle (double[] vt, double[] v0, double[] v1, double[] v2) {
    double d0, d1, d2;
    boolean has_neg, has_pos, zero;

    d0 = sign(vt, v0, v1);
    d1 = sign(vt, v1, v2);
    d2 = sign(vt, v2, v0);

    has_neg = (d0 < 0) || (d1 < 0) || (d2 < 0);
    has_pos = (d0 > 0) || (d1 > 0) || (d2 > 0);
    zero = (d0 == d1) && (d1 == d2) && (d0 == 0);

    return !(has_neg && has_pos) && !zero;
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
