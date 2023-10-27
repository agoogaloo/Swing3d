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
      vector[0] * matrix[0][0] + vector[1] * matrix[1][0] + vector[2] * matrix[2][0] + vector[4] * matrix[3][0],
      vector[0] * matrix[0][1] + vector[1] * matrix[1][1] + vector[2] * matrix[2][1] + vector[4] * matrix[3][1],
      vector[0] * matrix[0][2] + vector[1] * matrix[1][2] + vector[2] * matrix[2][2] + vector[4] * matrix[3][2],
      vector[0] * matrix[0][3] + vector[1] * matrix[1][3] + vector[2] * matrix[2][3] + vector[4] * matrix[3][3]
    };
  }
}
