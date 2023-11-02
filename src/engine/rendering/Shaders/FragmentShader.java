package engine.rendering.Shaders;

public abstract class FragmentShader {
  //TODO use vertexData
  //TODO make shaders write to vertex data instead of returning
  public abstract double[][][] compute(double[][][] frameBuffer, double[][] depthBuffer, int width, int height);

  double lerp(double a, double b, double t) {
    return a + (b-a)*t;
  }

  double invLerp(double a, double b, double s) {
    return (s-a)/(b-a);
  }
  double clamp(double min, double max, double t) {
    if(t < min) { return min; }
    if(t > max) { return max; }
    return t;
  }
}
