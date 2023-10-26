package engine.rendering;

import java.awt.image.BufferedImage;

import engine.rendering.Shaders.FragmentShader;
import engine.rendering.Shaders.VertexShader;
import engine.rendering.VertexTramsforms.VertexTransform;

public class RenderPipeline {
  double[][] vertices;
  double[][] frameBuffer;
  public RenderPipeline(double[][] vertices) {
    this.vertices = vertices;
  }

  public void applyVertexTransformations(VertexTransform[] vertexTransforms) {
    for (VertexTransform vertexTransform : vertexTransforms) {
      this.vertices = vertexTransform.compute(this.vertices);
    }
  }

  public void applyVertexShaders(VertexShader[] vertexShaders) {
    for (VertexShader shader : vertexShaders) {
      this.vertices = shader.compute(this.vertices);
    }
  }
  public void scan(double[][] frameBuffer) {
    //TODO draw pixels that fall within triangles
    this.frameBuffer = frameBuffer;
  }

  public void applyFragmentShaders(FragmentShader[] fragmentShaders) {
    for (FragmentShader shader : fragmentShaders) {
      this.frameBuffer = shader.compute(this.frameBuffer);
    }
  }

  public void display(BufferedImage frame) {
    //TODO write frame buffer to frame
  }
}
