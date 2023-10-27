package engine.rendering;

import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.rendering.Shaders.ExFragmentShader;
import engine.rendering.Shaders.FragmentShader;
import engine.rendering.Shaders.VertexShader;
import engine.rendering.VertexTramsforms.CullTriangles;
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
      new CullTriangles(), new NormalizeClipVertices(), new WindowTransform()
    };
    this.vertexShaders = new VertexShader[] {

    };
    this.fragmentShaders = new FragmentShader[] {
      new ExFragmentShader()
    };
  }

  public void render(BufferedImage frame, Mesh[] meshes, int vertexCount) {
    this.vertexPositions = new double[vertexCount][3];
    for(int i = 0; i < meshes.length; i++) {
      Mesh mesh = meshes[i];
      for(int j = 0; j < mesh.triangles.length; j++) {
        double[][] triangle = mesh.triangles[j];
        for (int k = 0; k < triangle.length; k++) {
          this.vertexPositions[j*3+k] = new double[] {
            triangle[k][0], triangle[k][1], triangle[k][2]
          };
        }
      }
    }
    
    renderPipeline.initialize(vertexPositions, frame.getWidth(), frame.getHeight());
    renderPipeline.projectVertices();
    renderPipeline.applyVertexTransformations(vertexTransforms);
    renderPipeline.applyVertexShaders(vertexShaders);
    renderPipeline.scan(true, true);
    renderPipeline.applyFragmentShaders(fragmentShaders);
    renderPipeline.display(frame);
  }
}
