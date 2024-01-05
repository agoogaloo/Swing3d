package engine.rendering.Shaders;

import engine.rendering.VertexData;

public abstract class FragmentShader {
  //TODO use vertexData
  //TODO make shaders write to vertex data instead of returning
  public abstract void compute();

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
