package engine.shapes;

public class Matrix {
  public static double[] multiplyVectorMatrix344(double[] vector, double[][] matrix) {
    return new double[] { 
      vector[0] * matrix[0][0] + vector[1] * matrix[1][0] + vector[2] * matrix[2][0] + matrix[3][0],
      vector[0] * matrix[0][1] + vector[1] * matrix[1][1] + vector[2] * matrix[2][1] + matrix[3][1],
      vector[0] * matrix[0][2] + vector[1] * matrix[1][2] + vector[2] * matrix[2][2] + matrix[3][2],
      //vector[0] * matrix[0][3] + vector[1] * matrix[1][3] + vector[2] * matrix[2][3] + matrix[3][3]
    };
  }
  
  public static double[] multiplyVectorMatrix444(double[] vector, double[][] matrix) {
    return new double[] { 
      vector[0] * matrix[0][0] + vector[1] * matrix[1][0] + vector[2] * matrix[2][0] + vector[3] * matrix[3][0],
      vector[0] * matrix[0][1] + vector[1] * matrix[1][1] + vector[2] * matrix[2][1] + vector[3] * matrix[3][1],
      vector[0] * matrix[0][2] + vector[1] * matrix[1][2] + vector[2] * matrix[2][2] + vector[3] * matrix[3][2],
      vector[0] * matrix[0][3] + vector[1] * matrix[1][3] + vector[2] * matrix[2][3] + vector[3] * matrix[3][3]
    };
  }

  public static double[][] pointAt(double[] position, double[] target, double[] up) {
    double[] newForward = Vector.subtract(target, position);
    newForward = Vector.normalize(newForward);

    double[] a = Vector.scalarMultiple(newForward, Vector.dotProduct(up, newForward));
    double[] newUp = Vector.subtract(up, a);
    newUp = Vector.normalize(newUp);

    double[] newRight = Vector.crossProduct(newUp, newForward);

    return new double[][] {
      { newRight[0], newRight[1], newRight[2], 0 },
      { newUp[0], newUp[1], newUp[2], 0 },
      { newForward[0], newForward[1], newForward[2], 0 }, 
      { position[0], position[1], position[2], 1 }
    };
  }

  public static double[][] quickInverse(double[][] matrix) {
    double[][] newMatrix = new double[4][4];
    newMatrix[0][0] = matrix[0][0]; newMatrix[0][1] = matrix[1][0]; newMatrix[0][2] = matrix[2][0]; newMatrix[0][3] = 0.0f;
		newMatrix[1][0] = matrix[0][1]; newMatrix[1][1] = matrix[1][1]; newMatrix[1][2] = matrix[2][1]; newMatrix[1][3] = 0.0f;
		newMatrix[2][0] = matrix[0][2]; newMatrix[2][1] = matrix[1][2]; newMatrix[2][2] = matrix[2][2]; newMatrix[2][3] = 0.0f;
		newMatrix[3][0] = -(matrix[3][0] * newMatrix[0][0] + matrix[3][1] * newMatrix[1][0] + matrix[3][2] * newMatrix[2][0]);
		newMatrix[3][1] = -(matrix[3][0] * newMatrix[0][1] + matrix[3][1] * newMatrix[1][1] + matrix[3][2] * newMatrix[2][1]);
		newMatrix[3][2] = -(matrix[3][0] * newMatrix[0][2] + matrix[3][1] * newMatrix[1][2] + matrix[3][2] * newMatrix[2][2]);
		newMatrix[3][3] = 1.0f;
    return newMatrix;
  }

  public static double[][] makeRotationMatrixY(double angle) {
    double yAngle = angle * 3.1415/180;
    return new double[][] {
      { Math.cos(yAngle), 0, Math.sin(yAngle), 0 },
      { 0, 1, 0, 0 },
      { -Math.sin(yAngle), 0, Math.cos(yAngle), 0 },
      { 0, 0, 0, 0 },
    };
  }
  public static double[][] makeRotationMatrixX(double angle) {
    double xAngle = angle * 3.1415/180;
    return new double[][] {
      { 1, 0, 0, 0 },
      { 0, Math.cos(xAngle), Math.sin(xAngle), 0 },
      { 0, -Math.sin(xAngle), Math.cos(xAngle), 0 },
      { 0, 0, 0, 0 },
    };
  }
}
