package engine.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public class Polygon {
    Vector2[] points;
    Color colour = Color.red;

    public Polygon(Vector2[] points) {
        this.points = points;
    }

    public void draw(Graphics2D g) {
        //converting the polygon into x and y arrays to be drawn
        int[] xpoints = new int[points.length], ypoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            xpoints[i] = (int) Math.round(points[i].x);
            ypoints[i] = (int) Math.round(points[i].y);
        }
        g.setColor(colour);
        g.fillPolygon(xpoints, ypoints, xpoints.length);

    }
}
