package engine.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public class Polygon2D {
    Vector2[] vertices;
    int[][] edges;
    Color colour = Color.red;

    public Polygon2D(Vector2[] vertices, int[][] edges, Color color) {
        this.vertices = vertices;
        this.edges = edges;
        this.colour = color;
    }
    public Polygon2D(int[][] vertices, int[][] edges, Color color) {
        this.vertices = new Vector2[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
          this.vertices[i] = new Vector2(vertices[i][0], vertices[i][1]);
        }
        this.edges = edges;
        this.colour = color;
    }

    public void draw(Graphics2D g) {
        //converting the polygon into x and y arrays to be drawn
        g.setColor(colour);
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            Vector2 v1 = vertices[edge[0]];
            Vector2 v2 = vertices[edge[1]];
            g.drawLine(v1.x, v1.y, v2.x, v2.y);
        }
    }
}
