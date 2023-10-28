package engine.rendering.Shaders;

public interface FragmentShader {
  //TODO turn into a class
  //TODO use vertexData
  //TODO make shaders write to vertex data instead of returning
  public abstract double[][][] compute(double[][][] frameBuffer, int width, int height);
}
