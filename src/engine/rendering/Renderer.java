package engine.rendering;

import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.rendering.Shaders.ExFragmentShader;
import engine.rendering.Shaders.FragmentShader;
import engine.rendering.Shaders.LinearLighting;
import engine.rendering.Shaders.VertexShader;
import engine.rendering.VertexTramsforms.CameraTransform;
import engine.rendering.VertexTramsforms.CullTriangles;
import engine.rendering.VertexTramsforms.NormalizeClipVertices;
import engine.rendering.VertexTramsforms.VertexTransform;
import engine.rendering.VertexTramsforms.WindowTransform;
import engine.shapes.Mesh;

public class Renderer {
  double[][] vertexPositions;
  double[][] surfaceColors;
  RenderPipeline renderPipeline;
  VertexTransform[] preProjection;
  VertexTransform[] postProjection;
  VertexShader[] vertexShaders;
  FragmentShader[] fragmentShaders;

  public Renderer() {
    renderPipeline = new RenderPipeline();

    this.preProjection = new VertexTransform[] {
      new CullTriangles(), new CameraTransform()
    };
    this.postProjection = new VertexTransform[] {
      new NormalizeClipVertices(),
      new WindowTransform()
    };
    this.vertexShaders = new VertexShader[] {
      new LinearLighting()
    };
    this.fragmentShaders = new FragmentShader[] {
      //new ExFragmentShader()
    };
  }

  public void render(BufferedImage frame, Mesh[] meshes, int vertexCount, double[] cameraPosition, double[] cameraDirection) {
    this.vertexPositions = new double[vertexCount][4];
    this.surfaceColors = new double[vertexCount/3][4];
    for(int i = 0; i < meshes.length; i++) {
      Mesh mesh = meshes[i];
      for(int j = 0; j < mesh.triangles.length; j++) {
        double[][] triangle = mesh.triangles[j];
        for (int k = 0; k < triangle.length; k++) {
          this.vertexPositions[j*3+k] = new double[] {
            triangle[k][0], triangle[k][1], triangle[k][2], 1
          };
        }
        surfaceColors[j] = mesh.colors[j];
      }
    }
    
    renderPipeline.initialize(vertexPositions, surfaceColors, cameraPosition, cameraDirection, frame.getWidth(), frame.getHeight());
    renderPipeline.computeSurfaceNormals();
    renderPipeline.applyVertexTransformations(preProjection);
    renderPipeline.projectVertices();
    renderPipeline.applyVertexTransformations(postProjection);
    renderPipeline.applyVertexShaders(vertexShaders);
    renderPipeline.scan(false, true);
    renderPipeline.applyFragmentShaders(fragmentShaders);
    renderPipeline.display(frame);
  }
}
