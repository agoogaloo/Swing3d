package engine.rendering.Shaders;

import engine.rendering.VertexData;

public abstract class VertexShader {
  public abstract void compute(VertexData vertexData);
}
