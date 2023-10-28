package engine.rendering;

import java.awt.image.BufferedImage;

import engine.rendering.Shaders.FragmentShader;
import engine.rendering.Shaders.VertexShader;
import engine.rendering.VertexTramsforms.VertexTransform;
import engine.shapes.Vector2;

public class RenderPipeline {
  VertexData vertexData;
  double[][][] frameBuffer;
  
  public void initialize(double[][] vertices, double[][] surfaceColors, int width, int height) {
    vertexData = new VertexData();
    vertexData.worldVertices = vertices;
    vertexData.vertices = new double[vertices.length][4];
    for(int i = 0; i < vertexData.worldVertices.length; i++) {
      vertexData.vertices[i] = vertexData.worldVertices[i];
    }
    vertexData.width = width;
    vertexData.height = height;
    vertexData.cameraPosition = new double[] { 0, 0, 0 };
    vertexData.surfaceColors = surfaceColors;
  }

  public void projectVertices() {
    double near = 0.1;
    double far = 1000;
    double fovRad = 90/2 * (3.1415/180);
    double fov = 1/Math.tan(fovRad);
    double aspectRatio = (double)vertexData.height/(double)vertexData.width;

    double[][] projectionMatrix = new double[][] {
      { aspectRatio * fov, 0, 0, 0 },
      { 0, fov, 0, 0 },
      { 0, 0, far/(far-near), 1 },
      { 0, 0, (-far * near)/(far-near), 0 },
    };

    for(int i = 0; i < vertexData.worldVertices.length; i++) {
      vertexData.vertices[i] = multiplyVectorMatrix4(vertexData.worldVertices[i], projectionMatrix);
    }    
  }

  private double[] multiplyVectorMatrix4(double[] vector, double[][] matrix) {
    double x = vector[0] * matrix[0][0] + vector[1] * matrix[1][0] + vector[2] * matrix[2][0] + matrix[3][0];
    double y = vector[0] * matrix[0][1] + vector[1] * matrix[1][1] + vector[2] * matrix[2][1] + matrix[3][1];
    double z = vector[0] * matrix[0][2] + vector[1] * matrix[1][2] + vector[2] * matrix[2][2] + matrix[3][2];
    double w = vector[0] * matrix[0][3] + vector[1] * matrix[1][3] + vector[2] * matrix[2][3] + matrix[3][3];
    if(w != 0) {
      x /= w; y /= w; z /= w; 
    }
    return new double[] { x, y, z, w };
  }

  public void computeSurfaceNormals() {
    vertexData.surfaceNormals = new double[vertexData.vertices.length/3][4];
    for(int i = 0; i < vertexData.vertices.length; i += 3) {
      double[] v0 = vertexData.vertices[i];
      double[] v1 = vertexData.vertices[i+1];
      double[] v2 = vertexData.vertices[i+2];

      double[] line1 = new double[] {
        v1[0] - v0[0], v1[1] - v0[1], v1[2] - v0[2],
      };
      double[] line2 = new double[] {
        v2[0] - v0[0], v2[1] - v0[1], v2[2] - v0[2],
      };

      double[] normal = new double[] {
        line1[1] * line2[2] - line1[2] * line2[1],
        line1[2] * line2[0] - line1[0] * line2[2],
        line1[0] * line2[1] - line1[1] * line2[0]
      };
      
      double l = Math.sqrt(normal[0]*normal[0] + normal[1]*normal[1] + normal[2]*normal[2]);

      normal = new double[] {
        normal[0]/l, normal[1]/l, normal[2]/l, l
      };

      vertexData.surfaceNormals[i/3] = normal;
    }
    //System.out.println(String.format("%f, %f, %f", vertexData.surfaceNormals[0][0], vertexData.surfaceNormals[0][1], vertexData.surfaceNormals[0][2]));
  }

  public void applyVertexTransformations(VertexTransform[] vertexTransforms) {
    for (VertexTransform vertexTransform : vertexTransforms) {
      vertexTransform.compute(vertexData);
    }
  }

  public void applyVertexShaders(VertexShader[] vertexShaders) {
    for (VertexShader shader : vertexShaders) {
      shader.compute(vertexData);
    }
  }

  public void scan(boolean drawEdges, boolean fill) {
    frameBuffer = new double[vertexData.width][vertexData.height][4];
    if(fill) {
      //TODO optimize to only check pixel inside of a triangle
      for(int x = 0; x < vertexData.width; x++) {
        for(int y = 0; y < vertexData.height; y++) {
          for(int i = 0; i < vertexData.vertices.length; i += 3) {
            if(vertexData.drawTriangles[i/3]) {
              double[] v0 = vertexData.vertices[i];
              double[] v1 = vertexData.vertices[i+1];
              double[] v2 = vertexData.vertices[i+2];
              if(PointInTriangle(new double[] { x, y }, v0, v1, v2)) {
                double[] color = vertexData.surfaceColors[i/3];
                frameBuffer[x][y] = color;
              }
            }
          }
        }
      }
    }
    if(drawEdges) {
      for(int i = 0; i < vertexData.vertices.length; i += 3) {
        if(vertexData.drawTriangles[i/3]) {
          double[] v0 = vertexData.vertices[i];
          double[] v1 = vertexData.vertices[i+1];
          double[] v2 = vertexData.vertices[i+2];
  
          drawLine(v1, v0, frameBuffer);
          drawLine(v2, v1, frameBuffer);
          drawLine(v0, v2, frameBuffer);
        }
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
          if(v0.x < vertexData.width && v0.y < vertexData.height && v0.x >= 0 && v0.y >= 0) {
            frameBuffer[v0.x][v0.y] = new double[] {
              1, 0, 0, 1
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
      this.frameBuffer = shader.compute(this.frameBuffer, vertexData.width, vertexData.height);
    }
  }

  public void display(BufferedImage frame) {
    int[] stackedFrame = new int[vertexData.width*vertexData.height];
    for(int x = 0; x < vertexData.width; x++) {
      for(int y = 0; y < vertexData.height; y++) {
        double[] color = frameBuffer[x][y];
        stackedFrame[y*vertexData.width+x] = getIntFromColor(
          (int)(Math.min(color[0],1)*255), 
          (int)(Math.min(color[1],1)*255), 
          (int)(Math.min(color[2],1)*255), 
          (int)(Math.min(color[3],1)*255) 
        );
      }
    }
    frame.setRGB(0, 0, vertexData.width, vertexData.height, stackedFrame, 0, vertexData.width);
  }

  int getIntFromColor(int alpha, int red, int green, int blue){
    alpha = (alpha << 24) & 0xFF000000; //Shift red 16-bits and mask out other stuff
    red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
    green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
    blue = blue & 0x000000FF; //Mask out anything not blue.

    return alpha | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
  }
}

