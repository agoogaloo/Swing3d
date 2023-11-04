package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;
import engine.shapes.Matrix;
import engine.shapes.Vector;

public class CameraTransform extends VertexTransform {
  public void compute() {
    double[] up = new double[] { 0, 1, 0 };
    double[] target = Vector.add(VertexData.cameraPosition, VertexData.lookDirection);
  
    double[][] cameraMatrix = Matrix.pointAt(VertexData.cameraPosition, target, up);
    double[][] viewMatrix = Matrix.quickInverse(cameraMatrix);

    for(int i = 0; i < VertexData.vertices.length; i++) {
      VertexData.vertices[i] = Matrix.multiplyVectorMatrix444(VertexData.vertices[i], viewMatrix);
    }
    VertexData.lightPlane[0] = Matrix.multiplyVectorMatrix444(VertexData.lightPlane[0], viewMatrix);
    VertexData.lightPlane[1] = Matrix.multiplyVectorMatrix444(VertexData.lightPlane[1], viewMatrix);
    VertexData.lightPlane[2] = Matrix.multiplyVectorMatrix444(VertexData.lightPlane[2], viewMatrix);
  }
}
