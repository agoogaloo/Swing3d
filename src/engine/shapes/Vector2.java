package engine.shapes;

public class Vector2 {
    public double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public boolean zero() {
        return (x == 0 && y == 0);
    }

    public void add(Vector2 vector) {
        x += vector.x;
        y += vector.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
