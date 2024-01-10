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

  public void rotateCamera(double[] angles) {
    rotation = Vector.add(rotation, angles);
    double[][] rotationMatrix;

    if(angles[0] != 0) {
      rotationMatrix = Matrix.makeRotationMatrixY(angles[0]);
      cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
    }
    if(angles[1] != 0) {
      rotationMatrix = Matrix.makeRotationMatrixX(angles[1]);
      cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
    }
  }

  public void setRotation(double[] angles) {
    rotateCamera(new double[] { 
      angles[0] - rotation[0],
      angles[1] - rotation[1],
      angles[2] - rotation[2],
    });
  }
}
