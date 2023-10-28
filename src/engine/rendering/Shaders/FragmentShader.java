package engine.rendering.Shaders;

public abstract class FragmentShader {
  //TODO use vertexData
  //TODO make shaders write to vertex data instead of returning
  public abstract double[][][] compute(double[][][] frameBuffer, int width, int height);
}
