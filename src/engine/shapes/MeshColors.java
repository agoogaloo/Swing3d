package engine.shapes;

import java.util.Arrays;
import java.util.Collections;

public class MeshColors {
  public static final double[][] defaultColor = new double[][] {
      { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
      { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
      { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
      { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
      { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
      { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
  };

  public static final double[][] red = new double[][] {
      { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
      { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
      { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
      { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
      { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
      { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
  };

  public static final double[][] green = new double[][] {
      { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
      { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
      { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
      { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
      { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
      { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
  };

  public static final double[][] blue = new double[][] {
      { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
      { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
      { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
      { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
      { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
      { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
  };

  public static final double[][] pink = new double[][] {
      { 1, 245 / 255f, 0, 120 / 255f }, { 1, 245 / 255f, 0, 120 / 255f },
      { 1, 245 / 255f, 0, 120 / 255f }, { 1, 245 / 255f, 0, 120 / 255f },
      { 1, 245 / 255f, 0, 120 / 255f }, { 1, 245 / 255f, 0, 120 / 255f },
      { 1, 245 / 255f, 0, 120 / 255f }, { 1, 245 / 255f, 0, 120 / 255f },
      { 1, 245 / 255f, 0, 120 / 255f }, { 1, 245 / 255f, 0, 120 / 255f },
      { 1, 245 / 255f, 0, 120 / 255f }, { 1, 245 / 255f, 0, 120 / 255f },
  };

  public static final double[][] lightTeal = Collections.nCopies(12, new double[]{1,71/255f,235/255f,1}).toArray(new double[12][3]);
  public static final double[][] darkBlue = Collections.nCopies(12, new double[]{1,0, 34/255f, 143/255f}).toArray(new double[12][3]);
  public static final double[][] midTeal = Collections.nCopies(12, new double[]{1,0, 139/255f, 245/255f}).toArray(new double[12][3]);
  


}
