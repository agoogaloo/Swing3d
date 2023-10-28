package engine.rendering.Shaders;

import engine.rendering.VertexData;

public interface VertexShader {
  //TODO turn into a class
  //TODO make shaders write to vertex data instead of returning
  public abstract double[][] compute(VertexData vertexData);
}
