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

    public void draw(Graphics2D g) {
        //converting the polygon into x and y arrays to be drawn
        g.setColor(colour);
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            Vector2 v1 = vertices[edge[0]];
            Vector2 v2 = vertices[edge[1]];
            g.drawLine((int)v1.x, (int)v1.y, (int)v2.x, (int)v2.y);
        }
    }
}
