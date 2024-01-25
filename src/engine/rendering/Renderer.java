package engine.rendering;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import engine.rendering.Shaders.*;
import engine.rendering.UI.TextObject;
import engine.rendering.VertexTramsforms.*;
import engine.shapes.Mesh;

public class Renderer {
  double[][] vertexPositions;
  double[][] surfaceColors;
  RenderPipeline renderPipeline;
  VertexTransform[] preProjection;
  VertexTransform[] postProjection;
  VertexTransform[] screenClipping;
  VertexShader[] vertexShaders;
  FragmentShader[] fragmentShaders;
  FragmentShader[] customShaders = new FragmentShader[] { };

  public Renderer() {
    renderPipeline = new RenderPipeline();

    this.preProjection = new VertexTransform[] {
      new CullTriangles(),
      new CameraTransform(), 
      new ClipTriangles(new double[] { 0, 0, 0.1 }, new double[] { 0, 0, 1 } ),
    };
    this.postProjection = new VertexTransform[] {
      new NormalizeClipVertices(),
      new WindowTransform()
    };
    this.vertexShaders = new VertexShader[] {

    };
    this.fragmentShaders = new FragmentShader[] {
      new LinearLighting(),
      new EdgeDetection(),
    };
  }

  public void render(BufferedImage frame, Mesh[] meshes, ArrayList<TextObject> text, double[] cameraPosition, double[] cameraDirection) {

    int vertexCount = 0;
    for(int i = 0; i < meshes.length; i++) {
      vertexCount += meshes[i].triangles.length*3;
    }
    this.vertexPositions = new double[vertexCount][4];
    this.surfaceColors = new double[vertexCount/3][4];
    int vertex = 0;
    for(int i = 0; i < meshes.length; i++) {
      Mesh mesh = meshes[i];
      for(int j = 0; j < mesh.triangles.length; j++) {
        double[][] triangle = mesh.triangles[j];
        for (int k = 0; k < triangle.length; k++) {
          this.vertexPositions[vertex] = new double[] {
            triangle[k][0], triangle[k][1], triangle[k][2], 1
          };
          vertex++;
        }
        surfaceColors[(vertex-1)/3] = mesh.colors[j];
      }
    }

    this.screenClipping = new VertexTransform[] {
      new ClipTriangles(new double[] { 0, 0, 0 }, new double[] { 0, 1, 0 } ),
      new ClipTriangles(new double[] { 0, (double)frame.getHeight()-1, 0 }, new double[] { 0, -1, 0 } ),
      new ClipTriangles(new double[] { 0, 0, 0 }, new double[] { 1, 0, 0 } ),
      new ClipTriangles(new double[] { (double)frame.getWidth()-1, 0, 0 }, new double[] { -1, 0, 0 } ),
    };
    
    renderPipeline.initialize(
      vertexPositions, surfaceColors, 
      cameraPosition, cameraDirection, 
      frame.getWidth(), frame.getHeight(), 
      new double[][] { { 0, 0, 0, 1 }, { 0, -1, 1.5, 1 }, { 1, -1, 1.75, 1 }, }
    );
    renderPipeline.computeSurfaceNormals();
    renderPipeline.applyVertexTransformations(preProjection);
    renderPipeline.projectVertices();
    renderPipeline.applyVertexTransformations(postProjection);
    renderPipeline.applyVertexTransformations(screenClipping);
    renderPipeline.applyVertexShaders(vertexShaders);
    renderPipeline.scan(false, true, true);
    renderPipeline.applyFragmentShaders(fragmentShaders);
    renderPipeline.applyFragmentShaders(customShaders);
    renderPipeline.display(frame);
    renderPipeline.drawUI(text, frame);
  }

  public void setShaders(FragmentShader[] shaders) {
    this.customShaders = shaders;
  }
}
