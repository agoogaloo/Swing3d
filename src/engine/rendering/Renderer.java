package engine.rendering;

import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.rendering.Shaders.FragmentShader;
import engine.rendering.Shaders.VertexShader;
import engine.rendering.VertexTramsforms.ClipVertices;
import engine.rendering.VertexTramsforms.VertexTransform;
import engine.rendering.VertexTramsforms.WindowTransform;

public class Renderer {
  double[][] vertexPositions;
  RenderPipeline renderPipeline;
  VertexTransform[] vertexTransforms;
  VertexShader[] vertexShaders;
  FragmentShader[] fragmentShaders;

  public Renderer() {
    this.vertexPositions = new double[][] {
      { 0.75, 0.75, 0.0, 1.0 },
      { 0.75, -0.75, 0.0, 1.0 },
      { -0.75, -0.75, 0.0, 1.0 },
    };
    this.vertexTransforms = new VertexTransform[] {
      new ClipVertices(), new WindowTransform()
    };
    this.vertexShaders = new VertexShader[] {

    };
    this.fragmentShaders = new FragmentShader[] {

    };
  }

  public void render(BufferedImage frame) {
    renderPipeline = new RenderPipeline(vertexPositions);
    renderPipeline.applyVertexTransformations(vertexTransforms);
    renderPipeline.applyVertexShaders(vertexShaders);
    renderPipeline.scan(new double[][] {{}});
    renderPipeline.applyFragmentShaders(fragmentShaders);
    renderPipeline.display(frame);
  }
}
