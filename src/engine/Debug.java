package engine;

import java.util.ArrayList;

public class Debug {
  public static ArrayList<double[]> worldPoints = new ArrayList<double[]>();
  public static ArrayList<double[]> points = new ArrayList<double[]>();

  public static void drawPoint(double[] point) {
    worldPoints.add(new double[] { point[0], point[1], point[2], 1 });
  }

  public static void clearPoints() {
    worldPoints = new ArrayList<double[]>();
    points = new ArrayList<double[]>();
  }
}
