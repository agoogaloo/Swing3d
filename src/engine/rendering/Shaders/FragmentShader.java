package engine.rendering.Shaders;

public abstract class FragmentShader {
  //TODO use vertexData
  //TODO make shaders write to vertex data instead of returning
  public abstract double[][][] compute(double[][][] frameBuffer, double[][] depthBuffer, int width, int height);
}
