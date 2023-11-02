package engine.shapes;

public class Vector {
  public static double[] add(double[] A, double[] B) {
    return new double[] {
      A[0] + B[0],
      A[1] + B[1],
      A[2] + B[2],
    };
  }
  public static double[] add4(double[] A, double[] B) {
    return new double[] {
      A[0] + B[0],
      A[1] + B[1],
      A[2] + B[2],
      A[3],
    };
  }

  public static double[] subtract(double[] A, double[] B) {
    return new double[] {
      A[0] - B[0],
      A[1] - B[1],
      A[2] - B[2],
    };
  }
  public static double[] subtract4(double[] A, double[] B) {
    return new double[] {
      A[0] - B[0],
      A[1] - B[1],
      A[2] - B[2],
      A[3],
    };
  }

  public static double[] scalarMultiple(double[] A, double k) {
    return new double[] {
      A[0]*k,
      A[1]*k,
      A[2]*k,
    };
  }
  public static double[] scalarMultiple4(double[] A, double k) {
    return new double[] {
      A[0]*k,
      A[1]*k,
      A[2]*k,
      A[3]
    };
  }

  public static double[] crossProduct(double[] A, double[] B) {
    return new double[] {
      A[1] * B[2] - A[2] * B[1],
      A[2] * B[0] - A[0] * B[2],
      A[0] * B[1] - A[1] * B[0]
    };
  }

  public static double[] normalize(double[] vector) {
    double l = Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1] + vector[2]*vector[2]);

    return new double[] {
      vector[0]/l, vector[1]/l, vector[2]/l
    };
  }

  public static double dotProduct(double[] A, double[] B) {
    return A[0]*B[0] + A[1]*B[1] + A[2] * B[2];
  }

  public static double[] planeIntersection(double[] planePoint, double[] planeNormal, double[] lineStart, double[] lineEnd) {
    double[] normal = normalize(planeNormal);
    
    double d = dotProduct(normal, planePoint);
    double ad = dotProduct(lineStart, normal);
    double bd = dotProduct(lineEnd, normal);
    double t = (d - ad) / (bd - ad);

    double[] lineVector = subtract(lineEnd, lineStart);
    double[] lineToIntersect = scalarMultiple(lineVector, t);
    return add4(lineStart, lineToIntersect);
  }

  public static double distToPlane(double[] normal, double[] planePoint, double[] point) {
    return (dotProduct(normal, point) - dotProduct(normal, planePoint));
  }
}
