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
    renderPipeline = new RenderPipeline();

    this.vertexTransforms = new VertexTransform[] {
      new ClipVertices(), new WindowTransform()
    };
    this.vertexShaders = new VertexShader[] {

    };
    this.fragmentShaders = new FragmentShader[] {

    };
  }

  public void render(BufferedImage frame) {
    renderPipeline.initialize(vertexPositions, frame.getWidth(), frame.getHeight());
    //System.out.println("Width: " + frame.getWidth() + ", Height: " + frame.getHeight());
    renderPipeline.applyVertexTransformations(vertexTransforms);
    renderPipeline.applyVertexShaders(vertexShaders);
    renderPipeline.scan();
    renderPipeline.applyFragmentShaders(fragmentShaders);
    renderPipeline.display(frame);
  }
}
