package engine.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public class Polygon3D {
    Vector3[] points;
    Color colour = Color.red;

    public Polygon3D(Vector3[] points, Color color) {
        this.points = points;
        this.colour = color;
    }

    public void draw(Graphics2D g, double focalLength) {
      this.getProjected(focalLength).draw(g);
    }

    public Polygon2D getProjected(double focalLength) {
      Vector2[] projectedPoints = new Vector2[points.length];
      for (int i = 0; i < points.length; i++) {
          projectedPoints[i] = points[i].getProjected(focalLength);
      }

      return new Polygon2D(projectedPoints, this.colour);
    }

    public void translate(Vector3 distance) {
      for (int i = 0; i < points.length; i++) {
        points[i].translate(distance);
      }
    }
}
