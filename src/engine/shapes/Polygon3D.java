package engine.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public class Polygon3D {
    Vector3[] vertices;
    int[][] edges;
    Color colour = Color.red;

    public Polygon3D(Vector3[] vertices, int[][] edges, Color color) {
        this.vertices = vertices;
        this.edges = edges;
        this.colour = color;
    }
    public Polygon3D(int[][] vertices, int[][] edges, Color color) {
        this.vertices = new Vector3[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
          this.vertices[i] = new Vector3(vertices[i][0], vertices[i][1], vertices[i][2]);
        }
        this.edges = edges;
        this.colour = color;
    }

    public Polygon2D getProjected(int focalLength) {
      Vector2[] projetedVertices = new Vector2[vertices.length];
      for (int i = 0; i < vertices.length; i++) {
        projetedVertices[i] = vertices[i].getProjected(focalLength);
      }

      return new Polygon2D(projetedVertices, this.edges, this.colour);
    }

    public void translate(Vector3 distance) {
      for (int i = 0; i < vertices.length; i++) {
        vertices[i].translate(distance);
      }
    }
}
