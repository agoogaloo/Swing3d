package engine.rendering.VertexTramsforms;

public interface VertexTransform {
	public abstract double[][] compute(double[][] vertices, int width, int height);
}
