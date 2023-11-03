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

  public void compute() {
    double[][] outputVertices = new double[VertexData.vertices.length*2][4];
    double[][] outputNormals = new double[VertexData.surfaceNormals.length*2][4];
    double[][] outputColors = new double[VertexData.surfaceColors.length*2][4];
    boolean[] outputTemp = new boolean[VertexData.drawTriangles.length*2];
    int triangleCount = 0;

    for(int i = 0; i < VertexData.vertices.length; i += 3) {
      double[] v0 = VertexData.vertices[i];
      double[] v1 = VertexData.vertices[i+1];
      double[] v2 = VertexData.vertices[i+2];

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
        outputNormals[triangleCount] = VertexData.surfaceNormals[i/3];
        outputColors[triangleCount] = VertexData.surfaceColors[i/3];
        outputTemp[triangleCount] = VertexData.drawTriangles[i/3];
        triangleCount++;
      } else if(insideCount == 1) {
        outputVertices[triangleCount*3] = insidePoints[0];
        outputVertices[triangleCount*3+1] = Vector.planeIntersection(planePoint, planeNormal, insidePoints[0], outsidePoints[0]);
        outputVertices[triangleCount*3+2] = Vector.planeIntersection(planePoint, planeNormal, insidePoints[0], outsidePoints[1]);
        outputNormals[triangleCount] = VertexData.surfaceNormals[i/3];
        outputColors[triangleCount] = VertexData.surfaceColors[i/3];
        outputTemp[triangleCount] = VertexData.drawTriangles[i/3];
        triangleCount++;     
      } else {
        v2 = Vector.planeIntersection(planePoint, planeNormal, insidePoints[0], outsidePoints[0]);
        outputVertices[triangleCount*3] = insidePoints[0];
        outputVertices[triangleCount*3+1] = insidePoints[1];
        outputVertices[triangleCount*3+2] = v2;
        outputNormals[triangleCount] = VertexData.surfaceNormals[i/3];
        outputColors[triangleCount] = VertexData.surfaceColors[i/3];
        outputTemp[triangleCount] = VertexData.drawTriangles[i/3];
        triangleCount++;        
        outputVertices[triangleCount*3] = insidePoints[1];
        outputVertices[triangleCount*3+1] = v2;
        outputVertices[triangleCount*3+2] = Vector.planeIntersection(planePoint, planeNormal, insidePoints[1], outsidePoints[0]);
        outputNormals[triangleCount] = VertexData.surfaceNormals[i/3];
        outputColors[triangleCount] = VertexData.surfaceColors[i/3];
        outputTemp[triangleCount] = VertexData.drawTriangles[i/3];
        triangleCount++;
      }
    }
    VertexData.vertices = new double[triangleCount*3][4];
    VertexData.surfaceNormals = new double[triangleCount][4];
    VertexData.surfaceColors = new double[triangleCount][4];
    VertexData.drawTriangles = new boolean[triangleCount];

    for (int j = 0; j < triangleCount; j++) {
      VertexData.vertices[j*3] = outputVertices[j*3];
      VertexData.vertices[j*3+1] = outputVertices[j*3+1];
      VertexData.vertices[j*3+2] = outputVertices[j*3+2];
      VertexData.surfaceNormals[j] = outputNormals[j];
      VertexData.surfaceColors[j] = outputColors[j];
      VertexData.drawTriangles[j] = outputTemp[j];
    }
    return;
  }
}
