package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;
import engine.shapes.Matrix;
import engine.shapes.Vector;

public class CameraTransform extends VertexTransform {
  public void compute(VertexData vertexData) {
    double[] up = new double[] { 0, 1, 0 };
    double[] target = Vector.add(vertexData.cameraPosition, vertexData.lookDirection);
  
    double[][] cameraMatrix = Matrix.pointAt(vertexData.cameraPosition, target, up);
    double[][] viewMatrix = Matrix.quickInverse(cameraMatrix);

    for(int i = 0; i < vertexData.vertices.length; i++) {
      vertexData.vertices[i] = Matrix.multiplyVectorMatrix444(vertexData.vertices[i], viewMatrix);
    }
  }
}
