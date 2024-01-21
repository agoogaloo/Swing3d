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
    double[][] rotationMatrix = Matrix.makeRotationMatrixY(angle);
    cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
    
    rotation[1] += angle;
    yaw += angle;
  }
  
  public void pitchCamera(double angle) {
    double xAngle = Math.cos(yaw*Math.PI/180)*angle;
    double zAngle = Math.sin(yaw*Math.PI/180)*angle;
    
    double[][] rotationMatrix;
    
    rotationMatrix = Matrix.makeRotationMatrixX(xAngle);
    cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
    rotationMatrix = Matrix.makeRotationMatrixZ(zAngle);
    cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);

    pitch += angle;
    rotation[0] += xAngle;
    rotation[2] += zAngle;
  }
}
