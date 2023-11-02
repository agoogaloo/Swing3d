package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;
import engine.shapes.Vector;

public class ClipTriangles extends VertexTransform {
  double[] planeNormal;
  double[] planePoint;

  public ClipTriangles(double[] planePoint, double[] planeNormal) {
    this.planeNormal = planeNormal;
    this.planePoint = planePoint;
  }

  public void compute(VertexData vertexData) {
    double[][] outputVertices = new double[vertexData.vertices.length*2][4];
    double[][] outputNormals = new double[vertexData.surfaceNormals.length*2][4];
    double[][] outputColors = new double[vertexData.surfaceColors.length*2][4];
    boolean[] outputTemp = new boolean[vertexData.drawTriangles.length*2];
    int triangleCount = 0;

    for(int i = 0; i < vertexData.vertices.length; i += 3) {
      double[] v0 = vertexData.vertices[i];
      double[] v1 = vertexData.vertices[i+1];
      double[] v2 = vertexData.vertices[i+2];

      double d0 = Vector.distToPlane(planeNormal, planePoint, v0);
      double d1 = Vector.distToPlane(planeNormal, planePoint, v1);
      double d2 = Vector.distToPlane(planeNormal, planePoint, v2);

      double[][] insidePoints = new double[3][4];
      double[][] outsidePoints = new double[3][4];
      int insideCount = 0, outsideCount = 0;

      if(d0 >= 0) { insidePoints[insideCount++] = v0; }
      else { outsidePoints[outsideCount++] = v0; }
      if(d1 >= 0) { insidePoints[insideCount++] = v1; }
      else { outsidePoints[outsideCount++] = v1; }
      if(d2 >= 0) { insidePoints[insideCount++] = v2; }
      else { outsidePoints[outsideCount++] = v2; }

      if(insideCount ==  0) {
        triangleCount += 0;
      } else if(insideCount == 3) {
        outputVertices[triangleCount*3] = v0;
        outputVertices[triangleCount*3+1] = v1;
        outputVertices[triangleCount*3+2] = v2;
        outputNormals[triangleCount] = vertexData.surfaceNormals[i/3];
        outputColors[triangleCount] = vertexData.surfaceColors[i/3];
        outputTemp[triangleCount] = vertexData.drawTriangles[i/3];
        triangleCount++;
      } else if(insideCount == 1) {
        outputVertices[triangleCount*3] = insidePoints[0];
        outputVertices[triangleCount*3+1] = Vector.planeIntersection(planePoint, planeNormal, insidePoints[0], outsidePoints[0]);
        outputVertices[triangleCount*3+2] = Vector.planeIntersection(planePoint, planeNormal, insidePoints[0], outsidePoints[1]);
        outputNormals[triangleCount] = vertexData.surfaceNormals[i/3];
        outputColors[triangleCount] = vertexData.surfaceColors[i/3];
        outputTemp[triangleCount] = vertexData.drawTriangles[i/3];
        triangleCount++;     
      } else {
        v2 = Vector.planeIntersection(planePoint, planeNormal, insidePoints[0], outsidePoints[0]);
        outputVertices[triangleCount*3] = insidePoints[0];
        outputVertices[triangleCount*3+1] = insidePoints[1];
        outputVertices[triangleCount*3+2] = v2;
        outputNormals[triangleCount] = vertexData.surfaceNormals[i/3];
        outputColors[triangleCount] = vertexData.surfaceColors[i/3];
        outputTemp[triangleCount] = vertexData.drawTriangles[i/3];
        triangleCount++;        
        outputVertices[triangleCount*3] = insidePoints[1];
        outputVertices[triangleCount*3+1] = v2;
        outputVertices[triangleCount*3+2] = Vector.planeIntersection(planePoint, planeNormal, insidePoints[1], outsidePoints[0]);
        outputNormals[triangleCount] = vertexData.surfaceNormals[i/3];
        outputColors[triangleCount] = vertexData.surfaceColors[i/3];
        outputTemp[triangleCount] = vertexData.drawTriangles[i/3];
        triangleCount++;
      }
    }
    vertexData.vertices = new double[triangleCount*3][4];
    vertexData.surfaceNormals = new double[triangleCount][4];
    vertexData.surfaceColors = new double[triangleCount][4];
    vertexData.drawTriangles = new boolean[triangleCount];

    for (int j = 0; j < triangleCount; j++) {
      vertexData.vertices[j*3] = outputVertices[j*3];
      vertexData.vertices[j*3+1] = outputVertices[j*3+1];
      vertexData.vertices[j*3+2] = outputVertices[j*3+2];
      vertexData.surfaceNormals[j] = outputNormals[j];
      vertexData.surfaceColors[j] = outputColors[j];
      vertexData.drawTriangles[j] = outputTemp[j];
    }
    return;
  }
}
