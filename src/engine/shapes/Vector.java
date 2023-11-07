package engine.shapes;

import engine.Debug;

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

  public static double[] copy(double[] A) {
    return new double[] {
      A[0],
      A[1],
      A[2]
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

  public static double[] computeNormalVector(double[] v0, double[] v1, double[] v2) {
    double[] line2 = new double[] {
      v1[0] - v0[0], v1[1] - v0[1], v1[2] - v0[2],
    };
    double[] line1 = new double[] {
      v2[0] - v0[0], v2[1] - v0[1], v2[2] - v0[2],
    };

    double[] normal = new double[] {
      line1[1] * line2[2] - line1[2] * line2[1],
      line1[2] * line2[0] - line1[0] * line2[2],
      line1[0] * line2[1] - line1[1] * line2[0]
    };
    
    double l = Math.sqrt(normal[0]*normal[0] + normal[1]*normal[1] + normal[2]*normal[2]);

    return new double[] {
      normal[0]/l, normal[1]/l, normal[2]/l, l
    };
  }

  public static boolean lineIntersectsTriangle(double[] v0, double[] v1, double[] v2, double[] lineStart, double[] lineEnd) {
    double[] normal = computeNormalVector(v0, v1, v2);

    double startDist = distToPlane(normal, v0, lineStart);
    double endDist = distToPlane(normal, v0, lineEnd);
    if(startDist * endDist > 0) { 
      return false; 
    }

   double[] intersection = planeIntersection(v0, normal, lineStart, lineEnd, true);

   return planePointOnTriangle(copy(v0), copy(v1), copy(v2), copy(intersection));
  }

  public static boolean planePointOnTriangle(double[] v0, double[] v1, double[] v2, double[] point) {
    v0 = subtract(v0, point);
    v1 = subtract(v1, point);
    v2 = subtract(v2, point);

    double[] u = crossProduct(v1, v2);
    double[] v = crossProduct(v2, v0);
    double[] w = crossProduct(v0, v1);

    if(dotProduct(u, v) < 0) { return false; }
    if(dotProduct(u, w) < 0) { return false; }

    return true;
  }

  public static double[] planeIntersection(double[] planePoint, double[] planeNormal, double[] lineStart, double[] lineEnd, boolean use3) {
    double[] normal = normalize(planeNormal);
    
    double d = dotProduct(normal, planePoint);
    double ad = dotProduct(lineStart, normal);
    double bd = dotProduct(lineEnd, normal);
    double t;
    if((bd - ad) == 0) {
      t = 0;
    } else {
      t = (d - ad) / (bd - ad);
    }

    double[] lineVector = subtract(lineEnd, lineStart);
    double[] lineToIntersect = scalarMultiple(lineVector, t);

    if(use3) {
      return add(lineStart, lineToIntersect);
    } 
    return add4(lineStart, lineToIntersect);
  }

  public static double distToPlane(double[] normal, double[] planePoint, double[] point) {
    return (dotProduct(normal, point) - dotProduct(normal, planePoint));
  }
}
