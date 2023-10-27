package engine.rendering.Shaders;

import engine.rendering.VertexData;

public interface VertexShader {
  public abstract double[][] compute(VertexData vertexData);
}
