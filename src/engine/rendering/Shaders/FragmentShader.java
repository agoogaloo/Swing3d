package engine.rendering.Shaders;

public interface FragmentShader {
  public abstract double[][][] compute(double[][][] pixels, int width, int height);
}
