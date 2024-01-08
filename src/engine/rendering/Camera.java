package engine.rendering;

import engine.shapes.Matrix;
import engine.shapes.Vector;

public class Camera {
  public double[] rotation = new double[] { 0, 0, 0 };
  public double[] cameraPosition = new double[] { 0, 0, 0 };
  public double[] cameraDirection = new double[] { 0, 0, 1 };

  public void cameraForward(double amount) {
    double[] forwardVector = Vector.scalarMultiple(cameraDirection, amount);
    cameraPosition = Vector.add(cameraPosition, forwardVector);
  }

  public void cameraMove(double x, double y) {
    double angle = rotation[0]*3.14159/180;
    double xDist = Math.cos(-angle) * x + Math.sin(angle) * y;
    double yDist = Math.sin(-angle) * x + Math.cos(angle) * y;

    cameraPosition[2] += xDist;
    cameraPosition[0] += yDist;
  }

  public void rotateCamera(double[] angles) {
    rotation = Vector.add(rotation, angles);
    if(rotation[0] >= -90 && rotation[0] <= 90 && rotation[1] >= -90 && rotation[1] <= 90) {
        double[][] rotationMatrix = Matrix.makeRotationMatrixY(angles[0]);
        cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
        rotationMatrix = Matrix.makeRotationMatrixX(angles[1]);
        cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
    } else {
        rotation = Vector.subtract(rotation, angles);
    }
  }
}
