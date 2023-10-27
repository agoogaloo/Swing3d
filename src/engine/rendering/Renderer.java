package engine.rendering;

import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.rendering.Shaders.ExFragmentShader;
import engine.rendering.Shaders.FragmentShader;
import engine.rendering.Shaders.VertexShader;
import engine.rendering.VertexTramsforms.ClipVertices;
import engine.rendering.VertexTramsforms.NormalizeClipVertices;
import engine.rendering.VertexTramsforms.VertexTransform;
import engine.rendering.VertexTramsforms.WindowTransform;
import engine.shapes.Mesh;

public class Renderer {
  double[][] vertexPositions;
  RenderPipeline renderPipeline;
  VertexTransform[] vertexTransforms;
  VertexShader[] vertexShaders;
  FragmentShader[] fragmentShaders;

  public Renderer() {
    renderPipeline = new RenderPipeline();

    this.vertexTransforms = new VertexTransform[] {
      new NormalizeClipVertices(), new WindowTransform()
    };
    this.vertexShaders = new VertexShader[] {

    };
    this.fragmentShaders = new FragmentShader[] {
      //new ExFragmentShader()
    };
  }

  public void render(BufferedImage frame, Mesh[] meshes, int vertexCount) {
    this.vertexPositions = new double[vertexCount][3];
    for(int i = 0; i < meshes.length; i++) {
      Mesh mesh = meshes[i];
      for(int j = 0; j < mesh.triangles.length; j++) {
        double[] triangle = mesh.triangles[j];
        this.vertexPositions[j*3] = new double[] {
          triangle[0], triangle[1], triangle[2]
        };
        this.vertexPositions[j*3+1] = new double[] {
          triangle[3], triangle[4], triangle[5]
        };
        this.vertexPositions[j*3+2] = new double[] {
          triangle[6], triangle[7], triangle[8]
        };
      }
    }
    
    renderPipeline.initialize(vertexPositions, frame.getWidth(), frame.getHeight());
    renderPipeline.projectVertices();
    renderPipeline.applyVertexTransformations(vertexTransforms);
    renderPipeline.applyVertexShaders(vertexShaders);
    renderPipeline.scan();
    renderPipeline.applyFragmentShaders(fragmentShaders);
    renderPipeline.display(frame);
  }
}
