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

    public void draw(Graphics2D g, double focalLength) {
      this.getProjected(focalLength).draw(g);
    }

    public Polygon2D getProjected(double focalLength) {
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
