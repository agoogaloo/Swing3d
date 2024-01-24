package engine.rendering;

import java.awt.image.BufferedImage;

import engine.rendering.Shaders.*;
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

  public Renderer() {
    renderPipeline = new RenderPipeline();

    this.preProjection = new VertexTransform[] {
      new CameraTransform(), 
      new CullTriangles(),
      new ClipTriangles(new double[] { 0, 0, 0.1 }, new double[] { 0, 0, 1 } ),
    };
    this.postProjection = new VertexTransform[] {
      new NormalizeClipVertices(),
      new WindowTransform()
    };
    this.vertexShaders = new VertexShader[] {

    };
    this.fragmentShaders = new FragmentShader[] {
      // new PalletinatorShader(),
      new LinearLighting(),
      // new NormalMap(),
      new DepthShader(),
      new FunkyBackground(),
      // new LowQualityColor(),
      // new HueShiftShader(),
      // new InvertColors(),
      // new Flip(),
      new Retical(),
    };
  }

  public void render(BufferedImage frame, Mesh[] meshes, double[] cameraPosition, double[] cameraDirection) {

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
    renderPipeline.display(frame);
  }
}
