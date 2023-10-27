package engine.rendering.VertexTramsforms;

public class CullTriangles implements VertexTransform {
  public double[][] compute(double[][] vertices, int width, int height) {
    double[][] tempVertices = new double[vertices.length][4];
    int vertexCount = 0;
    for(int i = 0; i < vertices.length; i += 3) {
      double[] v0 = vertices[i];
      double[] v1 = vertices[i+1];
      double[] v2 = vertices[i+2];

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
        normal[0]/l, normal[1]/l, normal[2]/l,
      };

      if(normal[2] < 0) {
        tempVertices[vertexCount] = v0;
        tempVertices[vertexCount+1] = v1;
        tempVertices[vertexCount+2] = v2;
        vertexCount += 3;
      }
    }
    double[][] outputVertices = new double[vertexCount][4];
    for (int i = 0; i < vertexCount; i++) {
      outputVertices[i] = tempVertices[i];
    }
    return outputVertices;
  }
}
