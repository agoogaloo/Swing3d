package engine.rendering;

import engine.shapes.Matrix;
import engine.shapes.Vector;

public class Camera {
  public double[] rotation = new double[] { 0, 0, 0 };
  public double yaw = 0;
  public double pitch = 0;
  public double[] cameraPosition = new double[] { 0, 0, 0 };
  public double[] cameraDirection = new double[] { 0, 0, 1 };

  public Camera() {
    setRotation(cameraDirection);
  }


  public void cameraForward(double amount) {
    double[] forwardVector = Vector.scalarMultiple(cameraDirection, amount);
    cameraPosition = Vector.add(cameraPosition, forwardVector);
  }
  
  public void setRotation(double[] angles) {
    double[][] rotationMatrix;
    rotation = angles;

    rotationMatrix = Matrix.makeRotationMatrixX(angles[0]);
    cameraDirection = Matrix.multiplyVectorMatrix344(new double[] { 0, 0, 1 }, rotationMatrix);
    
    rotationMatrix = Matrix.makeRotationMatrixY(angles[1]);
    cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);

    rotationMatrix = Matrix.makeRotationMatrixZ(angles[2]);
    cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
  }
  
  public void rotateCameraY(double angle) {
    yaw += angle;
    setRPRotation();
  }
  
  public void pitchCamera(double angle) {
    pitch += angle;
    setRPRotation();
  }
  
  void setRPRotation() {
    double[][] rotationMatrix;

    rotationMatrix = Matrix.makeRotationMatrixX(pitch);
    cameraDirection = Matrix.multiplyVectorMatrix344(new double[] { 0, 0, 1 }, rotationMatrix);
    
    rotationMatrix = Matrix.makeRotationMatrixY(yaw);
    cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
  }
}
