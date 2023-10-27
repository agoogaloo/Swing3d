package engine.rendering.VertexTramsforms;

import engine.rendering.VertexData;

public interface VertexTransform {
	public abstract double[][] compute(VertexData vertexData);
}
