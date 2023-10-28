package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public interface VertexTransform {
	//TODO turn into a class
  //TODO make shaders write to vertex data instead of returning
	public abstract double[][] compute(VertexData vertexData);
}
